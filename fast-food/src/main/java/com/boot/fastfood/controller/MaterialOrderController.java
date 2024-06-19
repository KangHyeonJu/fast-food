package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.service.ContractService;
import com.boot.fastfood.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MaterialOrderController {
    private final ContractService contractService;
    private final MaterialService materialService;
    @GetMapping("/order_plan")
    public String order_plan(Model model){
        List<Contract> contracts = contractService.getAllContract();
        List<MaterialOrderDto> materialOrderDtoList = new ArrayList<>();
        List<Materials> materials = materialService.getAllMaterial();

        for(Contract contract : contracts){
            MaterialOrderDto materialOrderDto = new MaterialOrderDto();
            materialOrderDto.setCtCode(contract.getCtCode());
            materialOrderDto.setCtAmount(contract.getCtAmount());
            materialOrderDto.setItems(contract.getItems());



            materialOrderDtoList.add(materialOrderDto);
        }

        model.addAttribute("contracts", materialOrderDtoList);
        model.addAttribute("materials", materials);
        return "material/Order_plan";
    }
}
