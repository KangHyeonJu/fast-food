package com.boot.fastfood.service;

import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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

    public Optional<Vendor> findByVdCode(String vdCode) {

        return Optional.ofNullable(vendorRepository.findByVdCode(vdCode));
    }

    public List<Vendor> findAllVendorsWithMaterials() {
        List<Vendor> vendors = vendorRepository.findAll();
        vendors.forEach(vendor -> Hibernate.initialize(vendor.getMaterials()));
        return vendors;
    }
}
