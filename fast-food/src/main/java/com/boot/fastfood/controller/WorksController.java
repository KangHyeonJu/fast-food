package com.boot.fastfood.controller;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.WorksRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.WorksService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/works")
public class WorksController {
    private final WorksService worksService;
    private final WorksRepository worksRepository;
    private final EmployeeService employeeService;


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
}
