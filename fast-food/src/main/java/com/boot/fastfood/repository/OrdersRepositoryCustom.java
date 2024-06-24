package com.boot.fastfood.repository;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.entity.Orders;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepositoryCustom {
    @Query("SELECT o FROM Orders o WHERE o.employee is not null")
    List<Orders> getOrderList(OrderSearchDto orderSearchDto);
}
