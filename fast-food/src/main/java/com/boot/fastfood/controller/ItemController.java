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

       // List<Process> processes = processService.search(pcCode, pcName, pcCnt);
        ProcessListDTO processList = processService.findList();

        List<Facility> facilityList= facilityService.findAll();

       List<ItemListDTO> items = itemService.search(itCode, itName, itType)
               .stream()
               .map(ItemListDTO::new)
               .toList();

        model.addAttribute("itemList", itemList);
        model.addAttribute("items", items);
        model.addAttribute("processList", processList);
        //model.addAttribute("processes", processes);
        model.addAttribute("facilityList", facilityList);

        return "system/routing";
    }


    @GetMapping("/process")
    public String itemList2(@RequestParam(required = false, name = "itCode") String itCode,
                            @RequestParam(required = false, name="itName") String itName,
                            @RequestParam(required = false, name="itType") String itType, Model model) {
        List<ItemListDTO> itemList = itemService.findAll()
                .stream()
                .map(ItemListDTO::new)
                .toList();

        MaterialsListDTO materialsList = materialsService.findList();
        List<Vendor> vendorList = vendorService.findAll();

        List<ItemListDTO> items = itemService.search(itCode, itName, itType)
                .stream()
                .map(ItemListDTO::new)
                .toList();

        model.addAttribute("itemList", itemList);
        model.addAttribute("items", items);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("vendorList", vendorList);

        return "system/process";
    }

}
