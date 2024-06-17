package com.boot.fastfood.service;

import com.boot.fastfood.entity.Vendor;
import com.boot.fastfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }
}
