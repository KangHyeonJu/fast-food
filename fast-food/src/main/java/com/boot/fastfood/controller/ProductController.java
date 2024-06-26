package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.ProcessRepository;
import com.boot.fastfood.repository.ProductionRepository;
import com.boot.fastfood.service.ProductionService;
import com.boot.fastfood.service.WorksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/machine")
@RequiredArgsConstructor
public class ProductController {
    private final ProductionService productionService;
    private final WorksService worksService;
    private final ProductionRepository productionRepository;

    @GetMapping("/productPlan")
    public String productPlan(Model model){
        List<ProductionDto> productions = productionService.getAllProductions();
        List<Works> works = worksService.findAll();
        model.addAttribute("works", works);
        model.addAttribute("productions", productions);
        return "ProductPages/productionPlan";
    }

    @GetMapping("/getWorks")
    @ResponseBody
    public List<Works> getWorksByPmCode(@RequestParam(name ="pmCode") String pmCode) {
        List<Works> works = worksService.findByPmCode(pmCode);
        return works;
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