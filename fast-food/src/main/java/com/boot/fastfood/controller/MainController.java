package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/item")
    public String mainPage(){
        return "item";
    }
    @GetMapping("/item1")
    public String maPage(){
        return "item1";
    }

    @GetMapping("/process")
    public String rocess(){
        return "process";
    }
    @GetMapping("/routing")
    public String routingP(){
        return "routing";
    }

    @GetMapping("/routingAdd")
    public String routingAddP(){
        return "routingAdd";
    }

    @GetMapping("/bom")
    public String bomP(){
        return "bom";
    }

    @GetMapping("/bom1")
    public String bP(){
        return "bom1";
    }

    @GetMapping("/code")
    public String codeP(){
        return "code";
    }

    @GetMapping("/vendor")
    public String vendorP(){
        return "vendor";
    }

    @GetMapping("/client")
    public String clientP(){
        return "client";
    }

}
