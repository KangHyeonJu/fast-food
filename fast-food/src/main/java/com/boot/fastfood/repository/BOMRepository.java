package com.boot.fastfood.repository;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.BOMId;
import com.boot.fastfood.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BOMRepository extends JpaRepository<BOM, String> {

    List<BOM> findByItems(Items itCode);
}
