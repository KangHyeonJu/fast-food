package com.boot.fastfood.service;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    public List<Orders> getOrderList(OrderSearchDto orderSearchDto){
        return ordersRepository.getOrderList(orderSearchDto);
    }
}
