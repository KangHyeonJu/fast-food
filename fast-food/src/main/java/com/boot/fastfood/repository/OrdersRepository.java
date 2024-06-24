package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, String>, OrdersRepositoryCustom {
    List<Orders> findByOdDate(LocalDate date);

    List<Orders> findByOdDateAndMaterials(LocalDate date, Materials materials);

    Orders findByOdCode(String odCode);

    List<Orders> findByOdDueDate(LocalDate today);

    List<Orders> findByMaterials_MtName(String mtName);

    List<Orders> findByOdDueDateGreaterThanEqual(LocalDate today);

    Orders findFirstByOdCode(String odCode);
}
