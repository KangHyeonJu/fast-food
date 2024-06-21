package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodeController {

    @GetMapping("/code")
    public String codeP(){
        return "system/code";
    }

    @GetMapping("/client")
    public String clientP(){
        return "system/client";
    }
}
