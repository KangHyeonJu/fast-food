package com.boot.fastfood.controller;

import com.boot.fastfood.dto.FacilityDto;
import com.boot.fastfood.dto.FacilitySearchDto;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.service.FacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    @GetMapping("/facility")
    public String facilityPage(FacilitySearchDto facilitySearchDto, Model model, FacilityDto facilityDto){
        List<Facility> facility = facilityService.getFacilityList(facilitySearchDto);

        model.addAttribute("facility", facility);
        model.addAttribute("facilitySearchDto", facilitySearchDto);
        model.addAttribute("facilityDto", facilityDto);

        return "facility/facility";
    }

    @PostMapping("/facility/new")
    public String newFacility(@Valid FacilityDto facilityDto, Model model){
        facilityService.saveFacility(facilityDto);


        return "redirect:/facility";
    }
}
