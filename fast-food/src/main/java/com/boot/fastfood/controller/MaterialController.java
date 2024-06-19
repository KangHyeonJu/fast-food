package com.boot.fastfood.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaterialController {

    //자재관리
    @GetMapping("/order")
    public String contract(){
        return "material/Order";
    }
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
    @GetMapping("/order_plan")
    public String order_plan(){
        return "material/Order_plan";
    }
}
