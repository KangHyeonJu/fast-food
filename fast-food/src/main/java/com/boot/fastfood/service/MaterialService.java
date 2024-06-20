package com.boot.fastfood.service;

import com.boot.fastfood.dto.OrderTodayDto;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.repository.ContractRepository;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.MaterialRepository;
import com.boot.fastfood.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final OrdersRepository ordersRepository;
    private final EmployeeRepository employeeRepository;

    public void orderAdd(String emName, List<OrderTodayDto> orderTodayDtoList){

        Employee employee = employeeRepository.findByEmName(emName);

        for(OrderTodayDto orderTodayDto : orderTodayDtoList){
            Orders orders = new Orders();
            orders.setMaterials(orderTodayDto.getMaterials());
            orders.setOdDate(LocalDate.now());
            orders.setOdCode("OD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            orders.setEmployee(employee);
            orders.setOdAmount(orderTodayDto.getOrderAmount());
            orders.setContract(orderTodayDto.getContract());

            ordersRepository.save(orders);

            orders.getContract().setCtStatus("생산중");
        }


    }

}
