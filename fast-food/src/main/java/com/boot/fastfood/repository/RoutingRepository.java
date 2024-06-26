package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutingRepository extends JpaRepository<Routing, Long> {

    @Query(value = "SELECT * FROM Routing where it_code = :itCode", nativeQuery = true)
    List<Routing> findByRoutingItCode(@Param("itCode") String itCode);

    @Query(value = "SELECT pc_code FROM Routing where it_code = :itCode", nativeQuery = true)
    List<String> findByItCodePcCode(@Param("itCode") String itCode);


    List<Routing> findByItems_ItCode(String itCode);

    Routing findByProcess_pcCode(String pcCode);

    @Query("SELECT r FROM Routing r WHERE r.items.itCode = :itCode AND r.process.pcCode = :pcCode")
    Routing findByItCodeAndPcCode(@Param("itCode") String itCode, @Param("pcCode") String pcCode);

    List<Routing> findByItems_ItCodeOrderBySequenceAsc(String itCode);
}

