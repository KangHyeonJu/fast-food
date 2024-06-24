package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items, String> {


    List<Items> findByItCode(String itCode);
    List<Items> findByItName(String itName);
    List<Items> findByItType(String itType);


    @Query(value = "SELECT * FROM items WHERE it_code LIKE :itCode AND it_name LIKE :itName AND it_type LIKE :itType", nativeQuery = true)
    List<Items> findItems(@Param("itCode") String itCode, @Param("itName") String itName, @Param("itType") String itType);

    @Query(value = "SELECT * FROM items WHERE it_code LIKE :itCode AND it_name LIKE :itName ", nativeQuery = true)
    List<Items> findByItCodeAndItName(@Param("itCode") String itCode, @Param("itName") String itName);

    @Query(value = "SELECT * FROM items WHERE it_code LIKE :itCode AND it_type LIKE :itType ", nativeQuery = true)
    List<Items> findByItCodeAndItType(@Param("itCode") String itCode, @Param("itType") String itType);

    @Query(value = "SELECT * FROM items WHERE it_type LIKE :itType AND it_name LIKE :itName ", nativeQuery = true)
    List<Items> findByItTypeAndItName(@Param("itType") String itType, @Param("itName") String itName);

}

