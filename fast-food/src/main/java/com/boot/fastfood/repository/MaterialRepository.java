package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Materials, String> {

    Materials findByMtName(String mtName);

    Materials findByMtCode(String mtCode);
}
