package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.entity.RoutingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutingRepository extends JpaRepository<Routing, RoutingId> {


    @Query(value = "SELECT * FROM Routing where it_code = :itCode", nativeQuery = true)
    List<Routing> findByItCode(@Param("itCode") String itCode);

   // Routing findByItCode(@Param("itCode") String itCode);
}
