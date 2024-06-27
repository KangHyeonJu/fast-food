package com.boot.fastfood.controller;

import com.boot.fastfood.dto.EmployeeDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

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

    @PostMapping(value = "/employee/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportCodeToExcel(@RequestParam(name = "emCode", required = false) List<String> emCode, HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeRepository.findByEmCodeIn(emCode);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Codes");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"직원코드", "직원명" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getEmCode());
            row.createCell(1).setCellValue(employee.getEmName());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=EmployeeList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
