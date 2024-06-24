package com.boot.fastfood.service;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
                productionStartDate = productionEndDate.minusDays( 3+ 2L *(int)Math.ceil((pmAmount-10000)/10000.0));
            }
        }else if (itType.equals("젤리스틱")){
            if (pmAmount <= 8000){
                productionStartDate = productionEndDate.minusDays(2);
            }else {
                productionStartDate = productionEndDate.minusDays( 2+ (int)Math.ceil((pmAmount-8000)/12000.0));
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
    }

    public List<Contract> getAllContract(){
        return contractRepository.findByCtStatus("준비중");
    }

    public List<Contract> searchContracts(ContractSearchDto searchDto) {
        return contractRepository.searchContracts(searchDto);
    }

}
