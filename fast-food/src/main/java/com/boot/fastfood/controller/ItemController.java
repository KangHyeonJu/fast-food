package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Item.ItemListDTO;
import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.dto.Materials.MaterialsListDTO;
import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessFacilityDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ProcessService processService;
    private final MaterialsService materialsService;
    private final FacilityService facilityService;
    private final VendorService vendorService;


    @GetMapping("/routing")
    public String itemList(@RequestParam(required = false, name = "itCode") String itCode,
                           @RequestParam(required = false, name="itName") String itName,
                           @RequestParam(required = false, name="itType") String itType, Model model) {

        List<ItemListDTO> itemList = itemService.findAll()
                        .stream()
                                .map(ItemListDTO::new)
                                        .toList();

        ProcessListDTO processList = processService.findList();

        List<Facility> facilityList= facilityService.findAll();

       // List<Items> items = itemService.search(itCode, itName, itType);

        model.addAttribute("itemList", itemList);
      //  model.addAttribute("items", items);
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

        MaterialsListDTO materialsList = materialsService.findList();
        List<Vendor> vendorList = vendorService.findAll();

        model.addAttribute("itemList", itemList);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("vendorList", vendorList);

        return "system/process";
    }

}
