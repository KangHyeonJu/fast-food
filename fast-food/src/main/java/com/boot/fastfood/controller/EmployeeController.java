package com.boot.fastfood.controller;

import com.boot.fastfood.dto.EmployeeDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/employee")
    public String employeePage(Model model){
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employee", employees);
        return "system/employee";
    }

    @PostMapping("/employee/new")
    public String newEmployee(EmployeeDto employeeDto, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "system/employee";
        }
        try {
            Employee employee = Employee.createEmployee(employeeDto);
            employeeService.saveEmployee(employee);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "system/employee";
        }

        return "redirect:/employee";
    }

    @PostMapping("/employee/delete")
    public ResponseEntity<?> deleteEmployee(@RequestBody List<String> emCodes) {
        try {
            for (String emCode : emCodes) {
                employeeService.deleteEmployee(emCode);
            }
            return ResponseEntity.ok("직원 삭제 완료");
        } catch (Exception e) {
            // 에러 로깅 (필요에 따라 추가)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 삭제 중 오류 발생");
        }
    }

}
