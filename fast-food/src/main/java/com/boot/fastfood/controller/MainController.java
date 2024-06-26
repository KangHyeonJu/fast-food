package com.boot.fastfood.controller;

import com.boot.fastfood.entity.Calendar;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.service.CalendarService;
import com.boot.fastfood.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final FacilityService facilityService;
    private final CalendarService calendarService;

    @GetMapping("/main")
    public String mainPage(Model model){
        List<Facility> facilities = facilityService.getAllFacility();

        model.addAttribute("facilities", facilities);

        List<Calendar> calendars = calendarService.getAllEvents();

        model.addAttribute("calendars", calendars);

        return "main/main";
    }


}

