package com.boot.fastfood.service;

import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public Facility findByFcCode(String fcCode) {
        Facility facility = facilityRepository.findById(fcCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + fcCode));

        return facility;
    }

    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

}
