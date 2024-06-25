package com.boot.fastfood.service;

import com.boot.fastfood.dto.*;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ItemsRepository itemsRepository;
    private final ClientsRepository clientsRepository;
    private final EmployeeRepository employeeRepository;
    private final BomService bomService;
    private final ProductionRepository productionRepository;
    private final OrdersRepository ordersRepository;
    public void saveContract(ContractDto contractDto) {
        // 고객 정보 설정
        Clients client = clientsRepository.findByClName(contractDto.getClName());

        // 제품 정보 설정
        Items item = itemsRepository.findByItName(contractDto.getItName());
        //작업자 정보 설정
        Employee employee = employeeRepository.findByEmName(contractDto.getEmName());

        if (item != null) {
            // 작업자 정보 설정

            // 현재 시간을 기준으로 문자열 생성
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String ctCode = "CT" + currentTime.format(formatter);

            // 수주 객체 생성 및 필드 설정
            Contract contract = new Contract();
            contract.setClients(client);
            contract.setItems(item);
            contract.setEmployee(employee);
            contract.setCtCode(ctCode);
            contract.setCtAmount(contractDto.getCtAmount());
            contract.setDeliveryPlace(contractDto.getDeliveryPlace());
            contract.setCtDate(LocalDate.now());
            contract.setDeliveryDate(contractDto.getDeliveryDate());
            contract.setCtStatus("준비중");

            // 저장
            System.out.println("마지막ㅇㅇㅇ");
            contractRepository.save(contract);
            System.out.println("수주가 저장되었습니다.");

            Production production = new Production();
            registerContractAndProduction(contract, production);
        } else {
            throw new IllegalArgumentException("제품 정보를 찾을 수 없습니다: A1");
        }
    }

    public void registerContractAndProduction(Contract contract, Production production) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String pmCode = "PM" + currentTime.format(formatter);

        //생산 종료일 계산(납품일로부터 1일 전)
        LocalDate deliveryDate = contract.getDeliveryDate();
        LocalDate productionEndDate = deliveryDate.minusDays(1);

        // 생산 시작일 계산 (생산종료일로 부터  2일전) - 수정해야함 임시 값
        LocalDate productionStartDate = productionEndDate.minusDays(2);

        // 제품 정보 설정
        Items item = contract.getItems();

        // 제품의 재고량
        int itStock = item.getItStock();

        // pmAmount 계산: ctAmount - itStock
        int pmAmount = contract.getCtAmount() - itStock ;
        if (pmAmount < 0){
            pmAmount = 0;
        }

        // Production 엔티티에 수주 정보 및 생산 일정 설정 후 저장
        production.setContract(contract);
        production.setPmCode(pmCode);
        production.setItName(item);
        production.setPmSDate(productionStartDate);
        production.setPmEDate(productionEndDate);
        production.setPmAmount(pmAmount);
        // 생산량 등 다른 필요한 정보 설정

        productionRepository.save(production);

        mtOrderPlan(contract);
    }

    public void mtOrderPlan(Contract contract){
        System.out.println("들어오나");
        Production production = productionRepository.findByContract(contract);

        List<BOM> bomList = bomService.getItemByBom(contract.getItems());

        for (int i = 0; i < bomList.size(); i++) {
            int odAmount = (int) Math.ceil(bomList.get(i).getMtAmount() * production.getPmAmount() * contract.getItems().getItEa());

            int week = production.getPmSDate().getDayOfWeek().getValue();

            if (week == 1 || week == 2 || week == 3){
                Orders findOrder = ordersRepository.findByOdDateAndMaterials(production.getPmSDate().minusDays(5), bomList.get(i).getMaterials());
                if(findOrder == null){
                    Orders orders = new Orders();
                    orders.setOdCode("OD" + i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                    orders.setOdDate(production.getPmSDate().minusDays(5));
                    orders.setMaterials(bomList.get(i).getMaterials());
                    orders.setOdState(false);
                    orders.setWhStatus(0);
                    orders.setOdAmount(odAmount);
                    ordersRepository.save(orders);
                }else {
                    Materials materials = findOrder.getMaterials();
                    int minOrder = materials.getMtMin();
                    int maxOrder = materials.getMtMax();

                    if(findOrder.getOdAmount() + odAmount > maxOrder){
                        int excessAmount = findOrder.getOdAmount()  + odAmount - maxOrder;
                        findOrder.setOdAmount(maxOrder);
                        ordersRepository.save(findOrder);

                        pushToNextDay(findOrder, excessAmount);
                    }else {
                        findOrder.setOdAmount(findOrder.getOdAmount() + odAmount);
                        ordersRepository.save(findOrder);
                    }
                }

            }else if(week == 4 || week == 5){
                Orders findOrder = ordersRepository.findByOdDateAndMaterials(production.getPmSDate().minusDays(3), bomList.get(i).getMaterials());
                if(findOrder == null){
                    Orders orders = new Orders();
                    orders.setOdCode("OD" + i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                    orders.setOdDate(production.getPmSDate().minusDays(3));
                    orders.setMaterials(bomList.get(i).getMaterials());
                    orders.setOdAmount(odAmount);
                    orders.setOdState(false);
                    orders.setWhStatus(0);
                    ordersRepository.save(orders);
                }else {
                    Materials materials = findOrder.getMaterials();
                    int minOrder = materials.getMtMin();
                    int maxOrder = materials.getMtMax();

                    if(findOrder.getOdAmount() + odAmount > maxOrder){
                        int excessAmount = findOrder.getOdAmount()  + odAmount - maxOrder;
                        findOrder.setOdAmount(maxOrder);
                        ordersRepository.save(findOrder);

                        pushToNextDay(findOrder, excessAmount);
                    }else {
                        findOrder.setOdAmount(findOrder.getOdAmount() + odAmount);
                        ordersRepository.save(findOrder);
                    }
                }
            }
        }
    }

    public void pushToNextDay(Orders orders, int amount){
        Orders nextDayOrder = new Orders();
        nextDayOrder.setMaterials(orders.getMaterials());
        nextDayOrder.setOdDate(orders.getOdDate().plusDays(1));
        nextDayOrder.setOdAmount(amount);
//        nextDayOrder.setContract(orderTodayDto.getContract());
        nextDayOrder.setOdCode("OD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        nextDayOrder.setWhStatus(0);
        nextDayOrder.setOdState(false);

        ordersRepository.save(nextDayOrder);
    }


    public List<Contract> getAllContract(){
        return contractRepository.findByCtStatus("준비중");
    }

    public List<Contract> searchContracts(ContractSearchDto searchDto) {
        return contractRepository.searchContracts(searchDto);
    }

}
