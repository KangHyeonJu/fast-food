package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Materials, String> {
    Materials findByMtName(String mtName);

    @Query("SELECT m FROM Materials m WHERE m.mtName NOT IN('포장지', 'Box')")
    List<Materials> findMtList();
}
