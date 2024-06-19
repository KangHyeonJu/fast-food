package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Production;
import com.boot.fastfood.repository.ProductionRepository;
import com.boot.fastfood.service.BomService;
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
    private final BomService bomService;
    private final ProductionRepository productionRepository;

    @GetMapping("/order_plan")
    public String order_plan(Model model){
        List<Contract> contracts = contractService.getAllContract();
        List<MaterialOrderDto> materialOrderDtoList = new ArrayList<>();

        for(Contract contract : contracts){
            MaterialOrderDto materialOrderDto = new MaterialOrderDto();
            materialOrderDto.setCtCode(contract.getCtCode());
            materialOrderDto.setItems(contract.getItems());

            Production production = productionRepository.findByContract(contract);
//            materialOrderDto.setAmount(production.getCtAmount() * contract.getItems().getItEA);
            materialOrderDto.setAmount(production.getPmAmount());

            List<BOM> bomList = bomService.getItemByBom(contract.getItems());
            materialOrderDto.setBomList(bomList);

            materialOrderDtoList.add(materialOrderDto);
        }

        model.addAttribute("contracts", materialOrderDtoList);
        return "material/Order_plan";
    }
}
