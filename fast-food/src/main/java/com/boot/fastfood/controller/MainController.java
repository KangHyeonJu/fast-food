package com.boot.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String mainPage(){return "main/main";}

    @GetMapping("/process")
    public String processPage(){
        return "system/process";
    }

    @GetMapping("/routing")
    public String maPage(){
        return "system/routing";
    }

    @GetMapping("/process1")
    public String rocess(){
        return "processs";
    }
    @GetMapping("/routing1")
    public String routingP(){
        return "routingg";
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
        return "system/code";
    }

    @GetMapping("/vendor")
    public String vendorP(){
        return "system/vendor";
    }

    @GetMapping("/name")
    public String cntP(){
        return "system/name";
    }


}

