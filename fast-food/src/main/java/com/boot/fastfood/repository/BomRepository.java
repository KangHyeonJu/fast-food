package com.boot.fastfood.repository;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BomRepository extends JpaRepository<BOM, Long> {

    List<BOM> findByItems_ItCode(String itCode);

    @Query("SELECT r FROM BOM r WHERE r.items.itCode = :itCode AND r.materials.mtCode = :mtCode")
    BOM findByItCodeAndMtCode(@Param("itCode") String itCode, @Param("mtCode") String mtCode);

}
