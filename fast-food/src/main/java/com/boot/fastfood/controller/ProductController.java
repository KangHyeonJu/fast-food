package com.boot.fastfood.controller;

import com.boot.fastfood.entity.Contract;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/machine")
public class ProductController {
//    private final ContractService contractService;


    @GetMapping("/productPlan")
    public String productPlan(Model model){
//        List<Contract> contracts = contractService.getAllContracts();
        return "ProductPages/ProductionPlan";
    }

    @GetMapping("/wash")
    public String wash(){
        return "ProductPages/Machine/Wash";
    }

    @GetMapping("/extractors")
    public String extractors(){
        return "ProductPages/Machine/Extractors";
    }

    @GetMapping("/extractors2")
    public String extractors2(){
        return "ProductPages/Machine/Extractors2";
    }

    @GetMapping("/filters")
    public String filters(){
        return "ProductPages/Machine/Filters";
    }

    @GetMapping("/sterilizer")
    public String sterilizer(){
        return "ProductPages/Machine/Sterilizer";
    }

    @GetMapping("/sterilizer2")
    public String sterilizer2(){
        return "ProductPages/Machine/Sterilizer2";
    }

    @GetMapping("/fillings")
    public String fillings(){
        return "ProductPages/Machine/Fillings";
    }

    @GetMapping("/frezzer")
    public String frezzer(){
        return "ProductPages/Machine/Frezzer";
    }

    @GetMapping("/checker")
    public String checker(){
        return "ProductPages/Machine/Checker";
    }

    @GetMapping("/packing")
    public String packing(){
        return "ProductPages/Machine/Packing";
    }

    @GetMapping("/endProcess")
    public String endProcess(){
        return "ProductPages/EndProcess";
    }

    @GetMapping("/calender")
    public String calender(){
        return "ProductPages/Calender";
    }
}
