package com.boot.fastfood.repository;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BomRepository extends JpaRepository<BOM, Long> {

    List<BOM> findByItems_ItCode(String itCode);

}
