package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Vendor.AddVendorDTO;
import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VendorApiController {

    private final VendorService vendorService;

    @PostMapping("/addVendor")
    public ResponseEntity<Vendor> save(@RequestBody AddVendorDTO dto) {
        Vendor vendor = vendorService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vendor);
    }
}
