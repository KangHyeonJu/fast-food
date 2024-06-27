package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items, String> {


    @Query(value = "SELECT i FROM Items i WHERE i.itCode LIKE %:itCode%")
    List<Items> findByItCode(@Param("itCode") String itCode);

    @Query(value = "SELECT i FROM Items i WHERE i.itName LIKE %:itName%")

    List<Items> findByItName(@Param("itName") String itName);
    
    @Query(value = "SELECT i FROM Items i WHERE i.itType LIKE %:itType%")
    List<Items> findByItType(@Param("itType") String itType);

    @Query("SELECT i FROM Items i WHERE i.itCode LIKE %:itCode% AND i.itName LIKE %:itName% AND i.itType LIKE %:itType%")
    List<Items> findByItCodeAndItNameAndItType(@Param("itCode") String itCode, @Param("itName") String itName, @Param("itType") String itType);

    @Query(value = "SELECT i FROM Items i WHERE i.itCode LIKE %:itCode% AND i.itName LIKE %:itName%")
    List<Items> findByItCodeAndItName(@Param("itCode") String itCode, @Param("itName") String itName);

    @Query(value = "SELECT i FROM Items i WHERE i.itCode LIKE %:itCode% AND i.itType LIKE %:itType%")
    List<Items> findByItCodeAndItType(@Param("itCode") String itCode, @Param("itType") String itType);

    @Query(value = "SELECT i FROM Items i WHERE i.itType LIKE %:itType% AND i.itName LIKE %:itName%")
    List<Items> findByItTypeAndItName(@Param("itType") String itType, @Param("itName") String itName);
    
    List<Items> findByItCodeIn(List<String> itCode);
}

