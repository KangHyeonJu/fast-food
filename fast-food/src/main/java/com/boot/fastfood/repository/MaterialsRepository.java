package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialsRepository extends JpaRepository<Materials, String> {
}
