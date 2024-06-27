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
    private final VendorRepository vendorRepository;


    private final ContractService contractService;
    private final BomService bomService;
    private final ProductionRepository productionRepository;
    private final MaterialRepository materialRepository;

    public void orderAdd(String emName, List<Orders> orderList){
        Employee employee = employeeRepository.findByEmName(emName);
        for (Orders orders : orderList){
            orders.setOdState(true);
            orders.setEmployee(employee);

            orders.setOdDueDate(orders.getProduction().getPmSDate());
            orders.getProduction().getContract().setCtStatus("생산중");
            ordersRepository.save(orders);
        }
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
        orders.setWhStatus(0);
        orders.setOdState(true);
        orders.setOdDueDate(LocalDate.now().plusDays(9));

        Vendor vendor = materials.getVendor(); // 자재에 연결된 Vendor 가져오기
        if (vendor != null) {
            vendor.setAlAmount(vendor.getAlAmount() + boxNum);
            vendorRepository.save(vendor);
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
        orders.setWhStatus(0);
        orders.setOdState(true);

        int week = LocalDate.now().getDayOfWeek().getValue();

        if (week == 1 || week == 2){
            orders.setOdDueDate(LocalDate.now().plusDays(3));
        }else if(week == 3 || week == 4 || week == 5){
            orders.setOdDueDate(LocalDate.now().plusDays(5));
        }


        Vendor vendor = materials.getVendor(); // 자재에 연결된 Vendor 가져오기
        if (vendor != null) {
            vendor.setAlAmount(vendor.getAlAmount() + wrapNum);
            vendorRepository.save(vendor);
        }

        ordersRepository.save(orders);
    }

    public List<Materials> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Materials> findByMtCode(String mtCode) {

        return Optional.ofNullable(materialRepository.findByMtCode(mtCode));
    }

}
