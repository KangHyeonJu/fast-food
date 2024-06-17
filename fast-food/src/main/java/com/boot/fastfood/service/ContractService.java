package com.boot.fastfood.service;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ClientsRepository;
import com.boot.fastfood.repository.ContractRepository;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.ItemsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ItemsRepository itemsRepository;
    private final ClientsRepository clientsRepository;
    private final EmployeeRepository employeeRepository;

    public void saveContract(ContractDto contractDto) {
        System.out.println("111111111");
        // 고객 정보 설정
        Clients client = clientsRepository.findByClName("OO업체");
        System.out.println("222222222");

        // 제품 정보 설정 (A1 코드로 고정된 제품 가져오기)
        Items item = itemsRepository.findByItCode("A1");
        System.out.println("3333333" + item);

        if (item != null) {
            System.out.println("들어오나?");
            // 담당자 정보 설정
            Employee employee = employeeRepository.findByEmName("김OO");
            System.out.println("1111111111111"+employee.getEmName());

            // 현재 시간을 기준으로 문자열 생성
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String ctCode = "OD" + currentTime.format(formatter);

            LocalDateTime currentDateTime = LocalDateTime.now();

            // 수주 객체 생성 및 필드 설정
            Contract contract = new Contract();
            contract.setClients(client);
            contract.setItems(item);
            contract.setEmployee(employee);
            contract.setCtCode(ctCode);
            contract.setCtAmount(contractDto.getCtAmount());
            contract.setDeliveryPlace(contractDto.getDeliveryPlace());
            contract.setCtDate(currentDateTime);
            contract.setDeliveryDate(contractDto.getDeliveryDate());
            contract.setCtStatus("생산중");

            // 저장
            System.out.println("마지막ㅇㅇㅇ");
            contractRepository.save(contract);
            System.out.println("수주가 저장되었습니다.");
        } else {
            throw new IllegalArgumentException("제품 정보를 찾을 수 없습니다: A1");
        }
    }
}
