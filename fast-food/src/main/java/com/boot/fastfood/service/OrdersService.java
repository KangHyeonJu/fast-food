package com.boot.fastfood.service;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    public List<Orders> getOrderList(OrderSearchDto orderSearchDto){
        return ordersRepository.getOrderList(orderSearchDto);
    }

    public Map<LocalDate, List<Orders>> getOrderPlanGroupedByDate() {
        List<Orders> orders = ordersRepository.findByOdState(false);
        return orders.stream().collect(Collectors.groupingBy(Orders::getOdDate));
    }

}
