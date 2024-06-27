package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, String>, OrdersRepositoryCustom {
    List<Orders> findByOdDateAndOdState(LocalDate date, boolean state);

//    @Query("SELECT o FROM Orders o WHERE o.materials.mtName != 'Box' AND o.materials.mtName != '포장지' AND o.odState = true ")
//    List<Orders> findByOdStateAndOdDate(boolean state, LocalDate date);

    @Query("SELECT o FROM Orders o WHERE o.materials.mtName != 'Box' AND o.materials.mtName != '포장지' AND o.odState = :state AND o.odDate = :date")
    List<Orders> findByOdStateAndOdDate(@Param("state") boolean state, @Param("date") LocalDate date);

    Orders findByOdDateAndMaterials(LocalDate date, Materials materials);

    Orders findByOdCode(String odCode);

    List<Orders> findByOdDueDate(LocalDate today);

    List<Orders> findByMaterials_MtName(String mtName);

    List<Orders> findByOdDueDateGreaterThanEqual(LocalDate today);

    Orders findByOdDateAndMaterialsAndOdCodeContaining(LocalDate date, Materials materials, String ND);

    List<Orders> findByOdState(boolean state);

    List<Orders> findByOdDateAndMaterialsAndOdState(LocalDate date, Materials materials, boolean state);
    
    Orders findFirstByOdCode(String odCode);

    List<Orders> findByOdCodeIn(List<String> odCode);
}
