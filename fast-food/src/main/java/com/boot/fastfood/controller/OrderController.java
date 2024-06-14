package com.boot.fastfood.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/contract")
    public String Contract(){
        return "orders/Contract";
    }

    @GetMapping("/contract_list")
    public String Contract_List(){
        return "orders/Contract_List";
    }

}
