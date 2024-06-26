package com.boot.fastfood.service;

import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
