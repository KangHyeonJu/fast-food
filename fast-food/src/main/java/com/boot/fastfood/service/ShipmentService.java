package com.boot.fastfood.service;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Shipment;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final EmployeeRepository employeeRepository;
    public List<Shipment> getShipmentList(ShipSearchDto shipSearchDto){
        return shipmentRepository.getShipmentList(shipSearchDto);
    }

    public List<Shipment> getshipCompletionList(ShipSearchDto shipSearchDto){
        return shipmentRepository.getshipCompletionList(shipSearchDto);
    }

    public List<Shipment> getTodaySthipList(){
        LocalDate today = LocalDate.now().plusDays(1);

        return shipmentRepository.findAllByContract_DeliveryDateAndSmStatues(today, false);
    }

    public void shipRegistration(String smCode, String emCode){
        Shipment shipment = shipmentRepository.findBySmCode(smCode);
        Employee employee = employeeRepository.findByEmCode(emCode);
        shipment.setSmDate(LocalDate.now());
        shipment.setSmStatues(true);
        shipment.setEmployee(employee);

        System.out.println("emCode: " + emCode);
        System.out.println("emName: " + employee.getEmName());


        shipmentRepository.save(shipment);
    }


}
