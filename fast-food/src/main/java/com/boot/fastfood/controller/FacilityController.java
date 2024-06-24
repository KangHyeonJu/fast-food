package com.boot.fastfood.controller;

import com.boot.fastfood.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

}
