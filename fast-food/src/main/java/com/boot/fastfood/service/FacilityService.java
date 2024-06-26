package com.boot.fastfood.service;

import com.boot.fastfood.constant.FcStatus;
import com.boot.fastfood.dto.FacilityDto;
import com.boot.fastfood.dto.FacilitySearchDto;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.repository.FacilityRepository;
import com.boot.fastfood.repository.FacilityRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public List<Facility> getFacilityList(FacilitySearchDto facilitySearchDto){
        return facilityRepository.getFacilityList(facilitySearchDto);
    }

    public void saveFacility(FacilityDto facilityDto){
        Facility facility = facilityDto.addFacility();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        facility.setFcCode("FC" + nowTime);
        facility.setFcStatus(FcStatus.STOP);
        facilityRepository.save(facility);
    }

    public List<Facility> getAllFacility(){
        return facilityRepository.findAll();
    }

    public Facility findByFcName(String fcName) {
        Facility facility = facilityRepository.findFacilityByFcName(fcName);

        return facility;
    }
    public Facility findByFcCode(String fcCode) {
        Facility facility = facilityRepository.findById(fcCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + fcCode));

        return facility;
    }

    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

}
