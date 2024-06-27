package com.boot.fastfood.service;

import com.boot.fastfood.dto.*;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
    private final RoutingRepository routingRepository;
    private final WorksRepository worksRepository;
    private final ProcessRepository processRepository;

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
            contractRepository.save(contract);
            System.out.println("수주가 저장되었습니다.");

            //생산 계획 생성
            Production production = new Production();

            // 제품의 재고량
            int itStock = item.getItStock();
            // 제품의 타입
            String itType = item.getItType();

            // pmAmount 계산: ctAmount - itStock // 하루 최대 생산량 걸고
            int pmAmount = contract.getCtAmount() * item.getItEa() - itStock;
            if (pmAmount < 0){
                pmAmount = 0;
            }

            // 하루 최대생산량 넘으면 작업 계획 생성 메서드 두번 돌림
            int more = 0;
            int i = 0;

            if (itType.contains("즙")){
                while (pmAmount > 10000){
                    more = pmAmount - 10000;
                    pmAmount = 10000;

                    production.setPmAmount(pmAmount);

                    registerContractAndProduction(contract, production, i);

                    pmAmount = more;

                    i++;
                }

                production.setPmAmount(pmAmount);
                registerContractAndProduction(contract, production, i);

            } else if (itType.contains("스틱")) {
                while (pmAmount > 12000){
                    more = pmAmount - 12000;
                    pmAmount = 12000;

                    production.setPmAmount(pmAmount);
                    registerContractAndProduction(contract, production, i);

                    pmAmount = more;

                    i++;
                }

                production.setPmAmount(pmAmount);
                registerContractAndProduction(contract, production, i);
            }
        } else {
            throw new IllegalArgumentException("제품 정보를 찾을 수 없습니다: A1");
        }
    }

    public void registerContractAndProduction(Contract contract, Production production, int i) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String pmCode = "PM" + currentTime.format(formatter) + i;

        // 제품 정보 설정
        Items item = contract.getItems();

        // 제품의 재고량
        int itStock = item.getItStock();

        // 제품의 타입
        String itType = item.getItType();

        // pmAmount 계산: ctAmount - itStock // 하루 최대 생산량 걸고
//        int pmAmount = contract.getCtAmount() * item.getItEa() - itStock;
//        if (pmAmount < 0){
//            pmAmount = 0;
//        }


        //생산 종료일 계산(납품일로부터 1일 전)
        LocalDate deliveryDate = contract.getDeliveryDate();
        LocalDate productionEndDate = deliveryDate.minusDays(1);



        // 생산 시작일 계산
        LocalDate productionStartDate = null;

        if (itType.equals("즙")){
            if (production.getPmAmount() <= 10000){
                productionStartDate = productionEndDate.minusDays(3);
            }else {
                productionStartDate = productionEndDate.minusDays( 3 + 2L * (int)Math.ceil((production.getPmAmount() -10000)/10000.0));
            }
        }else if (itType.equals("젤리스틱")){
            if (production.getPmAmount()  <= 8000){
                productionStartDate = productionEndDate.minusDays(2);
            }else {
                productionStartDate = productionEndDate.minusDays( 2 + (int)Math.ceil((production.getPmAmount() -8000)/12000.0));
            }
        }

        // Production 엔티티에 수주 정보 및 생산 일정 설정 후 저장
        production.setContract(contract);
        production.setPmCode(pmCode);
        production.setItName(item);
        production.setPmSDate(productionStartDate);
        production.setPmEDate(productionEndDate);
//        production.setPmAmount(pmAmount);

        // 생산량 등 다른 필요한 정보 설정
        productionRepository.save(production);

        //자재 발주
        mtOrderPlan(contract, production, i);


        //작업 계획 생성
        Works works = new Works();
        registerProductionsWorks(production, works, i);
    }

    public void registerProductionsWorks(Production production, Works works, int i) {
        // Production 객체에서 itName 가져오기
        Items item = production.getItName();

        // itName으로 itCode가져오기
        String itCode = item.getItCode();

        // itCode로 공정가져오기
        List<Routing> routingList = routingRepository.findByItems_ItCodeOrderBySequenceAsc(itCode);

        // itCode로 itType 가져오기
        String itType = item.getItName();

        // 날짜 초기화
        LocalDateTime sDate = production.getPmSDate().atTime(9, 0);
        LocalDateTime eDate = null;

        // 공정코드 준비
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + i;

        int input = 0;
        int output = 0;

        // 수주별 공정 생성
        for (Routing routing : routingList) {
            // 새로운 works 객체 생성
            Works currentWorks = new Works();

            currentWorks.setProduction(production); // 생산 코드 등록
            currentWorks.setEmployee(null); // 직원 초기화

            String pcCode = routing.getProcess().getPcCode(); // 공정 코드 가져옴
            String pcName = processRepository.findPcNameByPcCode(pcCode); // 공정 이름 가져옴

            // 공정 이름에 포함된 단어에 따라 공정 시간 계산
            if (pcName.contains("전처리") || pcName.contains("세척")) {
                // wkCode
                currentWorks.setWkCode("A1" + date);

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                input = (int) Math.ceil((production.getPmAmount() * 1.04 * 0.01 * 10) / 0.75);
                currentWorks.setWkInput(input);

                // eDate
                int time = (int) Math.ceil(input * 0.12);
                eDate = sDate.plusMinutes(time);
                currentWorks.setEDate(eDate);
                sDate = eDate;

                // wkOutput
                output = (int) Math.ceil(input * 0.75);
                currentWorks.setWkOutput(output);
                input = output;

                // def
                int def = (int) Math.ceil((production.getPmAmount() * 1.04 * 0.01 * 10) * 0.25);
                currentWorks.setDef(def);

                // defRate
                int defRate = 75;
                currentWorks.setDefRate(defRate + '%');

            } else if (pcName.contains("추출")) {
                // wkCode
                currentWorks.setWkCode("A3" + date);

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                int plusDays = 0;

                // eDate
                if (input > 1000){
                    plusDays = (int)Math.ceil((double) input /1000);
                }else {
                    plusDays = 1;
                }
                eDate = sDate.plusDays(plusDays);
                currentWorks.setEDate(eDate);
                sDate = eDate;

                // wkOutput
                output = (int) Math.ceil(input * 0.2);
                currentWorks.setWkOutput(output);
                input = output;

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            } else if (pcName.contains("여과")) {
                // wkCode
                currentWorks.setWkCode("A4" + date);

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                // eDate
                eDate = sDate.plusHours(4);
                currentWorks.setEDate(eDate);
                sDate = eDate;

                // wkOutput
                output = (int) Math.ceil(input * 0.5);
                currentWorks.setWkOutput(output);
                input = output;

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            } else if (pcName.contains("살균")) {
                // wkCode
                if (itType.contains("즙")) {
                    currentWorks.setWkCode("A5" + date);
                } else {
                    currentWorks.setWkCode("B3" + date);
                }

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                // eDate
                currentWorks.setEDate(sDate.plusHours(2));
                sDate = eDate;

                // wkOutput
                currentWorks.setWkOutput(input);

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            } else if (pcName.contains("충진")) {
                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                // eDate
                // wkCode
                int time = 0;
                if (itType.contains("즙")) {
                    currentWorks.setWkCode("A6" + date);
                    time = (int) Math.ceil(input * 0.048);
                    currentWorks.setEDate(sDate.plusMinutes(time));
                    sDate = eDate;
                } else {
                    currentWorks.setWkCode("B4" + date);
                    time = (int) Math.ceil(input * 0.03);
                    currentWorks.setEDate(sDate.plusMinutes(time));
                    sDate = eDate;
                }

                // wkOutput
                output = (int) (double) (input * 100);
                currentWorks.setWkOutput(output);
                input = output;

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            } else if (pcName.contains("검사")) {
                // wkCode
                if (itType.contains("즙")) {
                    currentWorks.setWkCode("A7" + date);
                } else {
                    currentWorks.setWkCode("B6" + date);
                }

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                // defRate
                int defRate = (int)(Math.random() * 3) + 1; // 1~3까지의 랜덤 정수
                currentWorks.setDefRate(defRate + '%');

                // def
                int def = (int)Math.ceil((input * ((double) defRate / 100)));
                currentWorks.setDef(def);

                // eDate
                int time = (int)Math.ceil(input * 0.012);
                currentWorks.setEDate(sDate.plusMinutes(time));
                sDate = eDate;

                // wkOutput
                currentWorks.setWkOutput(input - def);
                input = currentWorks.getWkOutput();

            } else if (pcName.contains("포장")) {

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                int pa = input - production.getPmAmount();
                currentWorks.setWkInput(production.getPmAmount());

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

                // eDate
                int time = (int)Math.ceil(((double) production.getPmAmount() / 30) * 0.375);
                currentWorks.setEDate(sDate.plusMinutes(time));
                sDate = eDate;

                // wkCode
                // wkOutput
                if (itType.contains("즙")) {
                    currentWorks.setWkCode("A8" + date);
                    currentWorks.setWkOutput(production.getPmAmount() / 30);
                } else {
                    currentWorks.setWkCode("B7" + date);
                    currentWorks.setWkOutput(production.getPmAmount() / 25);
                }

            } else if (pcName.contains("혼합")) {
                // wkCode
                currentWorks.setWkCode("B2" + date);

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                input = (int) Math.ceil(production.getPmAmount() * 1.04 * 0.01 * 5);
                currentWorks.setWkInput(input);

                // eDate
                int plusHours = 0;
                if (input > 60){
                    plusHours = (int)Math.ceil((double) input/60) * 8;
                }else {
                    plusHours = 8;
                }
                eDate = sDate.plusHours(plusHours);
                currentWorks.setEDate(eDate);
                sDate = eDate;

                // wkOutput
                currentWorks.setWkOutput(input);
                output = input;

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            } else if (pcName.contains("냉각")) {
                // wkCode
                currentWorks.setWkCode("B5" + date);

                // sDate
                currentWorks.setSDate(sDate);

                // wkInput
                currentWorks.setWkInput(input);

                // eDate
                int plusHours = 0;
                if (input > 60) {
                    plusHours = (int) Math.ceil((double) input / 60) * 8;
                } else {
                    plusHours = 8;
                }
                eDate = sDate.plusHours(plusHours);
                currentWorks.setEDate(eDate);
                sDate = eDate;

                // wkOutput
                currentWorks.setWkOutput(input);
                output = input;

                // def
                currentWorks.setDef(0);

                // defRate
                currentWorks.setDefRate(0);

            }

                worksRepository.save(currentWorks);
        }
    }

    //자재 발주 계획
    public void mtOrderPlan(Contract contract, Production production, int j){
        List<BOM> bomList = bomService.getItemByBom(contract.getItems());

        for (int i = 0; i < bomList.size(); i++) {
            System.out.println("잉1");
            int odAmount = (int) Math.ceil(bomList.get(i).getMtAmount() * production.getPmAmount());
            int week = production.getPmSDate().getDayOfWeek().getValue();

            if (week == 1 || week == 2 || week == 3 || week == 7){
                processOrder(production, odAmount, i, bomList, 2, j);

            }else if(week == 4 || week == 5){
                processOrder(production, odAmount, i, bomList, 0, j);
            }else if(week == 6){
                processOrder(production, odAmount, i, bomList, 1, j);
            }
        }
    }

    //주말 포함 시 leadTime
    public void processOrder(Production production, int odAmount, int i, List<BOM> bomList, int minusDay, int j){
        int leadTime = bomList.get(i).getMaterials().getLeadTime();
        Materials materials = bomList.get(i).getMaterials();

        System.out.println("잉2");

        Orders orders = new Orders();
        orders.setOdCode("OD" + i + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + j);
        orders.setOdDate(production.getPmSDate().minusDays(leadTime + minusDay));
        orders.setMaterials(materials);
        orders.setOdState(false);
        orders.setWhStatus(0);
        orders.setProduction(production);

        int minOrder = materials.getMtMin();
        int maxOrder = materials.getMtMax();

        List<Orders> findOrdersList = ordersRepository.findByOdDateAndMaterialsAndOdState(production.getPmSDate().minusDays(leadTime + minusDay), materials, false);

        if(!findOrdersList.isEmpty()){
            int sumAmount = 0;
            for(Orders findOrder : findOrdersList){
                sumAmount += findOrder.getOdAmount();
            }

            if(sumAmount + odAmount > maxOrder){
                int excessAmount = sumAmount + odAmount - maxOrder;
                orders.setOdAmount(odAmount - excessAmount);

                ordersRepository.save(orders);
                pushToNextDay(orders, excessAmount);
            }else {
                orders.setOdAmount(odAmount);
                ordersRepository.save(orders);
            }
        }else {
            if(odAmount > maxOrder){
                int excessAmount = odAmount - maxOrder;
                orders.setOdAmount(odAmount - excessAmount);

                ordersRepository.save(orders);
                pushToNextDay(orders, excessAmount);
            }else {
                orders.setOdAmount(odAmount);
                ordersRepository.save(orders);
            }
        }


    }


    //최대 주문량 초과 시 다음날에 추가
    public void pushToNextDay(Orders orders, int amount){
        Orders nextDayOrder = new Orders();

        nextDayOrder.setProduction(orders.getProduction());
        nextDayOrder.setMaterials(orders.getMaterials());
        nextDayOrder.setOdDate(orders.getOdDate().plusDays(1));
        nextDayOrder.setOdAmount(amount);
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
