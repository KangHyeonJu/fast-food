package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.service.MaterialsService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialsRepository extends JpaRepository<Materials, String> {

}
