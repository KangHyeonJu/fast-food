package com.boot.fastfood.repository;

import com.boot.fastfood.dto.FacilitySearchDto;
import com.boot.fastfood.entity.Facility;

import java.util.List;

public interface FacilityRepositoryCustom {
    List<Facility> getFacilityList(FacilitySearchDto facilitySearchDto);

}
