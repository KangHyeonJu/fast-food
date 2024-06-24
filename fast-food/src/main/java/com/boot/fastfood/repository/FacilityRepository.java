package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> , FacilityRepositoryCustom{
    Facility findFacilityByFcName(String fcName);
}
