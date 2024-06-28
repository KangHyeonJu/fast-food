package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> , FacilityRepositoryCustom{
    Facility findFacilityByFcName(String fcName);

    List<Facility> findByFcCodeIn(List<String> fcCode);

    Facility findByFcCode(String fcCode);
}
