package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class shipmentController {
    @GetMapping("/shipment")
    public String shipmentPage(){
        return "shipment/shipment";
    }
}
