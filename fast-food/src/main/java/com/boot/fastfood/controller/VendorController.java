package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Vendor.VendorListDTO;
import com.boot.fastfood.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping("/vendor")
    public String findAll(Model model) {
        List<VendorListDTO> vendorList = vendorService.findAll()
                .stream()
                .map(VendorListDTO::new)
                .toList();

        model.addAttribute("vendorList", vendorList);

        return "system/vendor";
    }
}
