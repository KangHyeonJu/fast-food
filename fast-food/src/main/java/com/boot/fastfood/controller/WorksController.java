package com.boot.fastfood.controller;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.WorksRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.WorksService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/works")
public class WorksController {
    private final WorksService worksService;
    private final WorksRepository worksRepository;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;


    @GetMapping("/workList")
    public String workList(Model model){
        //공정 불러옴
        List<Works> works = worksRepository.findAll();
        model.addAttribute("works", works);

        //작업자 불러옴
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        // 오늘 날짜 추가
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("today", today);
        return "ProductPages/workList";
    }

    @PostMapping("/addRSDate")
    @ResponseBody
    public void saveRSDate(@RequestBody Map<String, Object> data){
        String wkCode = (String) data.get("wkCode");
        String emName = (String) data.get("emName");

        Works works = worksRepository.findByWkCode(wkCode);
        Employee employee = employeeRepository.findByEmName(emName);

        worksService.saveRSDate(works, employee);
    }

    @PostMapping("/addREDate")
    @ResponseBody
    public void saveREDate(@RequestBody Map<String, Object> data){
        String wkCode = (String) data.get("wkCode");
        Works works = worksRepository.findByWkCode(wkCode);
        worksService.saveREDate(works);
    }
}
