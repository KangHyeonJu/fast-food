package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class facilityController {
    @GetMapping("/facility")
    public String facilityPage(){
        return "facility/facility";
    }
}
