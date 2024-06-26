package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Production;
import com.boot.fastfood.entity.Works;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorksRepository extends JpaRepository<Works, String> {
    Works findByWkCode(String wkCode);

    List<Works> findByProduction(Production production);
}
