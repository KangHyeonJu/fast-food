package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/machine")
@RequiredArgsConstructor
public class ProductController {
    private final ProductionService productionService;

    @GetMapping("/productPlan")
    public String productPlan(Model model){
        List<ProductionDto> productions = productionService.getAllProductions();
        model.addAttribute("productions", productions);
        return "productionPlan";
    }

    @GetMapping("/wash")
    public String wash(){
        return "wash";
    }

    @GetMapping("/extractors")
    public String extractors(){
        return "extractors";
    }

    @GetMapping("/extractors2")
    public String extractors2(){
        return "extractors2";
    }

    @GetMapping("/filters")
    public String filters(){
        return "filters";
    }

    @GetMapping("/sterilizer")
    public String sterilizer(){
        return "sterilizer";
    }

    @GetMapping("/sterilizer2")
    public String sterilizer2(){
        return "sterilizer2";
    }

    @GetMapping("/fillings")
    public String fillings(){
        return "fillings";
    }

    @GetMapping("/frezzer")
    public String frezzer(){
        return "frezzer";
    }

    @GetMapping("/checker")
    public String checker(){
        return "checker";
    }

    @GetMapping("/packing")
    public String packing(){
        return "packing";
    }

    @GetMapping("/endProcess")
    public String endProcess(){
        return "endProcess";
    }

    @GetMapping("/calender")
    public String calender(){
        return "calender";
    }


}
