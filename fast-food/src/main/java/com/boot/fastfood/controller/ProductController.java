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
        return "ProductPages/productionPlan";
    }

    @GetMapping("/wash")
    public String wash(){
        return "ProductPages/Machine/wash";
    }

    @GetMapping("/extractors")
    public String extractors(){
        return "ProductPages/Machine/extractors";
    }

    @GetMapping("/extractors2")
    public String extractors2(){
        return "ProductPages/Machine/extractors2";
    }

    @GetMapping("/filters")
    public String filters(){
        return "ProductPages/Machine/filters";
    }

    @GetMapping("/sterilizer")
    public String sterilizer(){
        return "ProductPages/Machine/sterilizer";
    }

    @GetMapping("/sterilizer2")
    public String sterilizer2(){
        return "ProductPages/Machine/sterilizer2";
    }

    @GetMapping("/fillings")
    public String fillings(){
        return "ProductPages/Machine/fillings";
    }

    @GetMapping("/frezzer")
    public String frezzer(){
        return "ProductPages/Machine/frezzer";
    }

    @GetMapping("/checker")
    public String checker(){
        return "ProductPages/Machine/checker";
    }

    @GetMapping("/packing")
    public String packing(){
        return "ProductPages/Machine/packing";
    }

    @GetMapping("/endProcess")
    public String endProcess(){
        return "ProductPages/endProcess";
    }

    @GetMapping("/calender")
    public String calender(){
        return "ProductPages/calender";
    }


}
