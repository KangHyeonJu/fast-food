package com.boot.fastfood.service;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        employee.setEmCode("EM" + date);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public void deleteEmployee(String emCode){
        Employee employee = employeeRepository.findByEmCode(emCode).orElseThrow();
        if (employee != null){
            employee.setEmCon(1);
            employeeRepository.save(employee);
        }
    }

}
