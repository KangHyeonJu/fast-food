package com.boot.fastfood.service;

import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public Facility findByFcName(String fcName) {
        Facility facility = facilityRepository.findFacilityByFcName(fcName);

        return facility;
    }

}
