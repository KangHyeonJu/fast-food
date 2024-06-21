package com.boot.fastfood.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaterialController {

    @GetMapping("/warehousing")
    public String Warehousing(){
        return "material/Warehousing";
    }
    @GetMapping("/release")
    public String Release(){
        return "material/Release";
    }
    @GetMapping("/material")
    public String Material(){
        return "material/Material";
    }

}
