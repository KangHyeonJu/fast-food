package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MonthChartDto;
import com.boot.fastfood.dto.YearChartDto;
import com.boot.fastfood.service.CurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CurrentController {
    private final CurrentService currentService;

    @GetMapping("/current")
    public String currentPage(Model model){
        int currentYear = LocalDate.now().getYear();
        Map<Integer, List<MonthChartDto>> monthlyContracts = currentService.getMonthCart(currentYear);
        Map<Integer, List<YearChartDto>> yearlyContracts = currentService.getYearCart();

        model.addAttribute("monthlyContracts", monthlyContracts);
        model.addAttribute("yearlyContracts", yearlyContracts);

        return "current/current";
    }
}
