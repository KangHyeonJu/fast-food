package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.ItemsRepository;
import com.boot.fastfood.repository.WorksRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.WorksService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/works")
public class WorksController {
    private final WorksService worksService;
    private final WorksRepository worksRepository;
    private final EmployeeService employeeService;
    private final ItemsRepository itemsRepository;


    @GetMapping("/workList")
    public String workList(Model model){
        //공정 불러옴
        List<Works> works = worksRepository.findAll();
        model.addAttribute("works", works);

        //작업자 불러옴
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        List<Items> items = itemsRepository.findAll();
        model.addAttribute("items", items);

        // 오늘 날짜 추가
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("today", today);
        return "ProductPages/workList";
    }
    @GetMapping("/searchWorkList")
    public String searchWorkList(
            @RequestParam(required = false, name = "wkCode") String wkCode,
            @RequestParam(required = false, name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            Model model) {

        List<Works> works = worksRepository.findAll();

        // 필터링 조건에 따라 조회 처리
        if (wkCode != null && !wkCode.isEmpty()) {
            works = works.stream()
                    .filter(wh -> wh.getWkCode().contains(wkCode))
                    .collect(Collectors.toList());
        }
        if (startTime != null) {
            works = works.stream()
                    .filter(wh -> wh.getSDate() != null && wh.getSDate().toLocalDate().equals(startTime))
                    .collect(Collectors.toList());
        }

        model.addAttribute("works", works);


        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "ProductPages/workList";  // HTML 템플릿 파일 이름
    }
}
