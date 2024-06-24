package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Item.ItemListDTO;
import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessFacilityDTO;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.service.FacilityService;
import com.boot.fastfood.service.ItemService;
import com.boot.fastfood.service.MaterialsService;
import com.boot.fastfood.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ProcessService processService;
    private final MaterialsService materialsService;
    private final FacilityService facilityService;


    @GetMapping("/routing")
    public String itemList(Model model) {
        List<ItemListDTO> itemList = itemService.findAll()
                        .stream()
                                .map(ItemListDTO::new)
                                        .toList();

        List<Process> processList = processService.findAll();
        /*
        ProcessDTO dto = new ProcessDTO();
        ProcessFacilityDTO processList = new ProcessFacilityDTO();
        for(Process pro : List) {
            dto.setFcName(pro.getFacilities().getFcName());
            dto.setPcName(pro.getPcName());
            dto.setPcCode(pro.getPcCode());
            dto.setPcCnt(pro.getPcCnt());
            processList.addProcess(dto);
        }

         */

        List<Facility> facilityList= facilityService.findAll();


        model.addAttribute("itemList", itemList);
        model.addAttribute("processList", processList);
        model.addAttribute("facilityList", facilityList);

        return "system/routing";
    }


/*
    @GetMapping("/itemSearch")
    public String itemSearch(@RequestParam(required = false, name = "itCode") String itCode,
                                        @RequestParam(required = false, name="itName") String itName,
                                        @RequestParam(required = false, name="itType") String itType, Model model) {
        List<Items> items = itemService.search(itCode, itName, itType);

        List<ProcessDTO> processList = processService.findAll()
                .stream()
                .map(ProcessDTO::new)
                .toList();

        model.addAttribute("itemList", items);
        model.addAttribute("processList", processList);

        return "system/routing";
    }

 */

    @GetMapping("/process")
    public String itemList2(Model model) {
        List<ItemListDTO> itemList = itemService.findAll()
                .stream()
                .map(ItemListDTO::new)
                .toList();

        List<MaterialsDTO> materialsList = materialsService.findAll()
                .stream()
                .map(MaterialsDTO::new)
                .toList();

        model.addAttribute("itemList", itemList);
        model.addAttribute("materialsList", materialsList);

        return "system/process";
    }

}
