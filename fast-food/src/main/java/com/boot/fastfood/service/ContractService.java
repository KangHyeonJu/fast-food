package com.boot.fastfood.service;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    private final ProductionRepository productionRepository;
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

        // 제품 정보 설정
        Items item = contract.getItems();

        // 제품의 재고량
        int itStock = item.getItStock();

        // 제품의 타입
        String itType = item.getItType();

        // pmAmount 계산: ctAmount - itStock
        int pmAmount = contract.getCtAmount() * item.getItEa() - itStock;
        if (pmAmount < 0){
            pmAmount = 0;
        }

        //생산 종료일 계산(납품일로부터 1일 전)
        LocalDate deliveryDate = contract.getDeliveryDate();
        LocalDate productionEndDate = deliveryDate.minusDays(1);

        // 생산 시작일 계산
        LocalDate productionStartDate = null;
        if (itType.equals("즙")){
            if (pmAmount <= 10000){
                productionStartDate = productionEndDate.minusDays(3);
            }else {
                productionStartDate = productionEndDate.minusDays( 3 + 2L * (int)Math.ceil((pmAmount-10000)/10000.0));
            }
        }else if (itType.equals("젤리스틱")){
            if (pmAmount <= 8000){
                productionStartDate = productionEndDate.minusDays(2);
            }else {
                productionStartDate = productionEndDate.minusDays( 2 + (int)Math.ceil((pmAmount-8000)/12000.0));
            }
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

        //작업 계획 생성
        Works works = new Works();
        registerProductionsWorks(production, works);
    }

    //작업 계획 생성
    public void registerProductionsWorks(Production production, Works works) {
        // Production 객체에서 itName 가져오기
        Items item = production.getItName();

        // itName으로 itCode가져오기
        String itCode = item.getItCode();

        //itCode로 공정가져오기
        List<Routing> routingList = routingRepository.findByItems_ItCodeOrderBySequenceAsc(itCode);

        //itCode로 itType 가져오기
        String itType = item.getItName();

        //날짜 초기화
        LocalDateTime sDate = production.getPmSDate().atTime(9, 0);
        LocalDateTime eDate = production.getPmEDate().atTime(9, 0);

        //공정코드 준비
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int input = 0;
        int output = 0;

        //수주별 공정 생성
        for (Routing routing : routingList) {
            works.setProduction(production); //생산 코드 등록
            works.setEmployee(null); //직원 초기화

            String pcCode = routing.getProcess().getPcCode(); //공정 코드 가져옴
            String pcName = processRepository.findPcNameByPcCode(pcCode); // 공정 이름 가져옴

            //공정 이름에 포함된 단어에 따라 공정 시간 계산
            if (pcName.contains("전처리")) {
                //wkCode
                works.setWkCode("A1" + date);

                //sDate
                works.setSDate(sDate);

                //wkInput
                input = (int) Math.ceil((production.getPmAmount() * 1.04 * 0.01 * 10) / 0.75);
                works.setWkInput(input);

                //wkOutput
                output = (int) Math.ceil(input * 0.75);
                works.setWkOutput(output);
                input = output;

                //eDate
                int time = (int) Math.ceil(input * 0.12);
                works.setEDate(sDate.plusMinutes(time));
                sDate = eDate;

                //def
                int def = (int) Math.ceil((production.getPmAmount() * 1.04 * 0.01 * 10) * 0.25);
                works.setDef(def);

                //defRate
                int defRate = 75;
                works.setDefRate(defRate + '%');

            } else if (pcName.contains("추출")) {
                //wkCode
                works.setWkCode("A3" + date);

                //sDate
                works.setSDate(sDate);

                //wkInput
                works.setWkInput(input);

                //wkOutput
                output = (int) Math.ceil(input * 0.2);
                works.setWkOutput(output);
                input = output;

                //eDate
                works.setEDate(sDate.plusDays(1));
                sDate = eDate;

                //def
                works.setDef(0);

                //defRate
                works.setDefRate(0);

            } else if (pcName.contains("여과")) {
                //wkCode
                works.setWkCode("A4" + date);

                //sDate
                works.setSDate(sDate);

                //wkInput
                works.setWkInput(input);

                //wkOutput
                output = (int) Math.ceil(input * 0.5);
                works.setWkOutput(output);
                input = output;

                //eDate
                works.setEDate(sDate.plusHours(4));
                sDate = eDate;

                //def
                works.setDef(0);

                //defRate
                works.setDefRate(0);

            } else if (pcName.contains("살균")) {
                //wkCode
                if (itType.equals("즙")) {
                    works.setWkCode("A5" + date);
                } else {
                    works.setWkCode("B3" + date);
                }

                //sDate
                works.setSDate(sDate);

                //wkInput
                works.setWkInput(input);

                //wkOutput
                works.setWkOutput(input);

                //eDate
                works.setEDate(sDate.plusHours(2));
                sDate = eDate;

                //def
                works.setDef(0);

                //defRate
                works.setDefRate(0);

            } else if (pcName.contains("충진")) {
                //wkCode
                if (itType.equals("즙")) {
                    works.setWkCode("A6" + date);
                } else {
                    works.setWkCode("B4" + date);
                }

                //sDate
                works.setSDate(sDate);

                //wkInput
                works.setWkInput(input);

                //wkOutput
                output = (int) (double) (input * 100);
                works.setWkOutput(output);
                input = output;

                //eDate
                int time = (int) Math.ceil(output * 0.048);
                works.setEDate(sDate.plusMinutes(time));
                sDate = eDate;

                //def
                works.setDef(0);

                //defRate
                works.setDefRate(0);

            } else if (pcName.contains("검사")){
                //wkCode
                if (itType.equals("즙")) {
                    works.setWkCode("A7" + date);
                } else {
                    works.setWkCode("B6" + date);
                }

                //sDate
                works.setSDate(sDate);

                //wkInput
                works.setWkInput(input);

                //defRate
                int defRate = (int)(Math.random() * 3) + 1; //1~3까지의 랜덤 정수
                works.setDefRate(defRate + '%');

                //def
                int def = (int)Math.ceil((input * ((double) defRate /100)));
                works.setDef(def);

                //wkOutput
                works.setWkOutput(input - def);
                input = output;

                //eDate
                int time = (int)Math.ceil(input * 0.012);
                works.setEDate(sDate.plusMinutes(time));
                sDate = eDate;

            } else if (pcName.contains("포장")){
                //wkCode
                //wkOutput
                if (itType.equals("즙")) {
                    works.setWkCode("A8" + date);
                    works.setWkOutput(production.getPmAmount()/30);
                } else {
                    works.setWkCode("B7" + date);
                    works.setWkOutput(production.getPmAmount()/25);
                }

                //sDate
                works.setSDate(sDate);

                //wkInput
                int pa = input - production.getPmAmount();
                works.setWkInput(production.getPmAmount());

                //def
                works.setDef(0);

                //defRate
                works.setDefRate(0);

                //eDate
                int time = (int)Math.ceil(((double) production.getPmAmount() /30) * 0.375);
                works.setEDate(sDate.plusMinutes(time));
                sDate = eDate;
            }

            worksRepository.save(works);

        }
    }

    public List<Contract> getAllContract(){
        return contractRepository.findByCtStatus("준비중");
    }

    public List<Contract> searchContracts(ContractSearchDto searchDto) {
        return contractRepository.searchContracts(searchDto);
    }

}
