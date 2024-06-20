package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
}
