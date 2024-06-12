package com.boot.fastfood.controller.orderController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/contract")
    public String Contract(){
        return "orders/Contract";
    }
}
