package com.boot.fastfood.service;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.dto.OrderTodayDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final OrdersRepository ordersRepository;
    private final EmployeeRepository employeeRepository;

    private final ContractService contractService;
    private final BomService bomService;
    private final ProductionRepository productionRepository;
    private final MaterialRepository materialRepository;

    public void orderAdd(String emName, List<OrderTodayDto> orderTodayDtoList){
        Employee employee = employeeRepository.findByEmName(emName);

        for (int i=0; i<orderTodayDtoList.size(); i++){
            if(orderTodayDtoList.get(i).getOrderAmount() > 0){

                //어제 저장한 값 있으면 지우기
                Orders yesterdayOrders = ordersRepository.findByOdDateAndMaterialsAndOdCodeContaining(LocalDate.now(), orderTodayDtoList.get(i).getMaterials(), "_ND" );
                if(yesterdayOrders != null){
                    ordersRepository.delete(yesterdayOrders);
                }

                Orders orders = new Orders();
                orders.setContract(orderTodayDtoList.get(i).getContract());

                orders.setMaterials(orderTodayDtoList.get(i).getMaterials());
                orders.setOdDate(LocalDate.now());
                orders.setOdCode("OD" + i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                orders.setEmployee(employee);
                orders.setOdAmount(orderTodayDtoList.get(i).getOrderAmount());

                Production production = productionRepository.findByContract(orders.getContract());
                orders.setOdDueDate(production.getPmSDate());

                ordersRepository.save(orders);
                orders.getContract().setCtStatus("생산중");
            }
        }
    }

    public Map<String, Object> getOrderPlan(){
        List<Contract> contracts = contractService.getAllContract();
        List<MaterialOrderDto> materialOrderDtoList = new ArrayList<>();
        List<Materials> materialsList = materialRepository.findMtList();

        //수주별 아이템 BOM 구하기
        for (Contract contract : contracts) {
            MaterialOrderDto materialOrderDto = new MaterialOrderDto();
            materialOrderDto.setContract(contract);
            materialOrderDto.setItems(contract.getItems());

            Production production = productionRepository.findByContract(contract);
            materialOrderDto.setProduction(production);
            materialOrderDto.setAmount(production.getPmAmount() * contract.getItems().getItEa());

            List<BOM> bomList = bomService.getItemByBom(contract.getItems());
            String[][] bomAmount = new String[bomList.size()][2];

            for (int i = 0; i < bomList.size(); i++) {
                bomAmount[i][0] = bomList.get(i).getMaterials().getMtName();
                bomAmount[i][1] = Integer.toString((int) Math.ceil(bomList.get(i).getMtAmount() * production.getPmAmount() * contract.getItems().getItEa()));
            }
            materialOrderDto.setBomList(bomList);
            materialOrderDto.setBomListAmount(bomAmount);

            materialOrderDtoList.add(materialOrderDto);
        }

        List<OrderTodayDto> orderTodayDtoList = new ArrayList<>();

        //원자재 리스트
        for (Materials materials : materialsList){
            OrderTodayDto orderTodayDto = new OrderTodayDto();
            orderTodayDto.setMaterials(materials);
            orderTodayDtoList.add(orderTodayDto);
        }

        for (MaterialOrderDto materialOrderDto : materialOrderDtoList) {
            String[][] todayOrder = materialOrderDto.getBomListAmount();
            //요일 구하기(월~일 = 1~7)
            int week = materialOrderDto.getProduction().getPmSDate().getDayOfWeek().getValue();

            //주말 포함 시 +2일
            if ((week == 1 || week == 2 || week == 3) && materialOrderDto.getProduction().getPmSDate().minusDays(5).equals(LocalDate.now()) ||
                    (week == 4 || week == 5) && materialOrderDto.getProduction().getPmSDate().minusDays(3).equals(LocalDate.now())) {

                //같은 원자재면 더하기
                for (OrderTodayDto orderTodayDto : orderTodayDtoList) {
                    orderTodayDto.setContract(materialOrderDto.getContract());
                    int amount = orderTodayDto.getOrderAmount();
                    for (int i = 0; i < todayOrder.length; i++) {
                        if (Objects.equals(todayOrder[i][0], orderTodayDto.getMaterials().getMtName())) {
                            amount += Integer.parseInt(todayOrder[i][1]);
                            orderTodayDto.setOrderAmount(amount);
                        }
                    }
                }

                //어제 pushNextDay로 저장한 값이 있다면 더하기
                for (OrderTodayDto orderTodayDto : orderTodayDtoList) {
                    Orders orders = ordersRepository.findByOdDateAndMaterialsAndOdCodeContaining(LocalDate.now(), orderTodayDto.getMaterials(), "_ND");

                    if (orders != null) {
                        orderTodayDto.setOrderAmount(orderTodayDto.getOrderAmount() + orders.getOdAmount());
                    }
                }
            }
        }

        //각 원자재별 최소 주문 수량 미만, 최대 주문 수량 초과시 다음날로
        for(OrderTodayDto orderTodayDto : orderTodayDtoList){
            Materials materials = orderTodayDto.getMaterials();
            int minOrder = materials.getMtMin();
            int maxOrder = materials.getMtMax();

            if (orderTodayDto.getOrderAmount() != 0 && orderTodayDto.getOrderAmount() < minOrder){
                pushToNextDay(orderTodayDto, minOrder);
            }else if(orderTodayDto.getOrderAmount() > maxOrder){
                int excessAmount = orderTodayDto.getOrderAmount() - maxOrder;
                orderTodayDto.setOrderAmount(maxOrder);
                pushToNextDay(orderTodayDto, excessAmount);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("materialOrderDtoList", materialOrderDtoList);
        result.put("materialsList", materialsList);
        result.put("orderTodayDtoList", orderTodayDtoList);

        return result;
    }

    public void pushToNextDay(OrderTodayDto orderTodayDto, int amount){
        Orders nextDayOrder = new Orders();

        nextDayOrder.setMaterials(orderTodayDto.getMaterials());
        nextDayOrder.setOdDate(LocalDate.now().plusDays(1));
        nextDayOrder.setOdAmount(amount);
        nextDayOrder.setContract(orderTodayDto.getContract());
        nextDayOrder.setOdCode("OD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_ND");
        nextDayOrder.setWhStatus(0);

        ordersRepository.save(nextDayOrder);
    }

    public void orderBox(String emName, int boxNum){
        Employee employee = employeeRepository.findByEmName(emName);
        Materials materials = materialRepository.findByMtName("Box");
        Orders orders = new Orders();

        orders.setOdCode("OD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        orders.setMaterials(materials);
        orders.setEmployee(employee);
        orders.setOdAmount(boxNum);
        orders.setOdDate(LocalDate.now());

        int week = LocalDate.now().getDayOfWeek().getValue();
        if(week == 1 || week == 2 || week == 3){
            orders.setOdDueDate(LocalDate.now().plusDays(5));
        }else if(week == 4 || week == 5){
            orders.setOdDueDate(LocalDate.now().plusDays(3));
        }
        ordersRepository.save(orders);
    }

    public void orderWrap(String emName, int wrapNum){
        Employee employee = employeeRepository.findByEmName(emName);
        Materials materials = materialRepository.findByMtName("포장지");
        Orders orders = new Orders();

        orders.setOdCode("OD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        orders.setMaterials(materials);
        orders.setEmployee(employee);
        orders.setOdAmount(wrapNum);
        orders.setOdDate(LocalDate.now());
        orders.setOdDueDate(LocalDate.now().plusDays(9));

        ordersRepository.save(orders);
    }

    public List<Materials> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Materials> findByMtCode(String mtCode) {

        return Optional.ofNullable(materialRepository.findByMtCode(mtCode));
    }

}
