package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Production;
import com.boot.fastfood.entity.Works;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorksRepository extends JpaRepository<Works, String> {
    Works findByWkCode(String wkCode);

    List<Works> findByProduction(Production production);
    
    List<Works> findByProductionPmCode(String pmCode);

    List<Works> findBySDateOrEDate(LocalDateTime date);
}
