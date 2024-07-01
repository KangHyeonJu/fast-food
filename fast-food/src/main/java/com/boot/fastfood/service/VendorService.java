package com.boot.fastfood.service;

import com.boot.fastfood.dto.Item.AddItemDTO;
import com.boot.fastfood.dto.Vendor.AddVendorDTO;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Optional<Vendor> findByVdCodeOptional(String vdCode) {

        return Optional.ofNullable(vendorRepository.findByVdCode(vdCode));

    }
    
    public Vendor findByVdCode(String vdCode) {
        Vendor vendor = vendorRepository.findById(vdCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + vdCode));

        return vendor;
    }

    public List<Vendor> findAllVendorsWithMaterials() {
        List<Vendor> vendors = vendorRepository.findAll();
        vendors.forEach(vendor -> Hibernate.initialize(vendor.getMaterials()));
        return vendors;
    }

    public Vendor save(AddVendorDTO dto) {
        Vendor vendor = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        vendor.setVdCode("VD" + nowTime);
        vendor.setAlAmount(0);

        return vendorRepository.save(vendor);
    }
}
