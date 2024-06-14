package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class currentController {
    @GetMapping("/current")
    public String currentPage(){
        return "current/current";
    }
}
