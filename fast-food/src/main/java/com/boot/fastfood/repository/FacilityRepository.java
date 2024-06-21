package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, String> {

    Facility findFacilityByFcName(String fcName);
}
