package com.boot.fastfood.repository;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.entity.Orders;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<Orders> getOrderList(OrderSearchDto orderSearchDto);
}
