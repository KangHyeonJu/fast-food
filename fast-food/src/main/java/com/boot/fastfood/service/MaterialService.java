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
                Orders orders = new Orders();
                orders.setContract(orderTodayDtoList.get(i).getContract());

                orders.setMaterials(orderTodayDtoList.get(i).getMaterials());
                orders.setOdDate(LocalDate.now());
                orders.setOdCode("OD" + i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                orders.setEmployee(employee);
                orders.setOdAmount(orderTodayDtoList.get(i).getOrderAmount());

                ordersRepository.save(orders);
                orders.getContract().setCtStatus("생산중");
            }
        }
    }

    public Map<String, Object> getOrderPlan(){
        List<Contract> contracts = contractService.getAllContract();
        List<MaterialOrderDto> materialOrderDtoList = new ArrayList<>();
        List<Materials> materialsList = materialRepository.findMtList();

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

        for (Materials materials : materialsList){
            OrderTodayDto orderTodayDto = new OrderTodayDto();
            orderTodayDto.setMaterials(materials);
            orderTodayDtoList.add(orderTodayDto);
        }

        for (MaterialOrderDto materialOrderDto: materialOrderDtoList){
            if(materialOrderDto.getProduction().getPmSDate().minusDays(3).equals(LocalDate.now())){
                String[][] todayOrder = materialOrderDto.getBomListAmount();

                for (OrderTodayDto orderTodayDto : orderTodayDtoList){
                    orderTodayDto.setContract(materialOrderDto.getContract());
                    int amount = orderTodayDto.getOrderAmount();
                    for(int i=0; i<todayOrder.length; i++) {
                        if (Objects.equals(todayOrder[i][0], orderTodayDto.getMaterials().getMtName())) {
                            amount += Integer.parseInt(todayOrder[i][1]);
                            orderTodayDto.setOrderAmount(amount);
                        }

                    }
                }

            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("materialOrderDtoList", materialOrderDtoList);
        result.put("materialsList", materialsList);
        result.put("orderTodayDtoList", orderTodayDtoList);

        return result;
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
//        materials.setMtStock(materials.getMtStock() + boxNum);

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
//        materials.setMtStock(materials.getMtStock() + wrapNum);

        ordersRepository.save(orders);
    }

    public List<Materials> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Materials> findByMtCode(String mtCode) {

        return Optional.ofNullable(materialRepository.findByMtCode(mtCode));
    }

}
