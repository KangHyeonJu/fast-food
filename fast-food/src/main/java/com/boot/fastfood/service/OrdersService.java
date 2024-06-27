package com.boot.fastfood.service;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.dto.OrderSummaryDto;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
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

    public Map<LocalDate, List<OrderSummaryDto>> getOrderPlanGroupedByDate() {
        List<Orders> orders = ordersRepository.findByOdState(false);
        Map<LocalDate, List<Orders>> ordersByDate = orders.stream().collect(Collectors.groupingBy(Orders::getOdDate));

        Map<LocalDate, List<OrderSummaryDto>> summaryByDate = new HashMap<>();

        for (Map.Entry<LocalDate, List<Orders>> entry : ordersByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Orders> ordersList = entry.getValue();

            Map<Materials, Integer> materialsAmountMap = new HashMap<>();

            for (Orders order : ordersList) {
                Materials materials = order.getMaterials();
                int amount = order.getOdAmount();
                materialsAmountMap.put(materials, materialsAmountMap.getOrDefault(materials, 0) + amount);
            }

            List<OrderSummaryDto> summaryDtoList = materialsAmountMap.entrySet().stream()
                    .map(e -> new OrderSummaryDto(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            summaryByDate.put(date, summaryDtoList);
        }

        return summaryByDate;
    }

    public List<OrderSummaryDto> getOrderSumToday(){
        List<Orders> orderToday = ordersRepository.findByOdDateAndOdState(LocalDate.now(), false);

        Map<Materials, Integer> materialsIntegerMap = orderToday.stream().collect(Collectors.groupingBy(Orders::getMaterials, Collectors.summingInt(Orders::getOdAmount)));

        return materialsIntegerMap.entrySet().stream()
                .map(e -> new OrderSummaryDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<Orders> getOrdersByStateAndDate(boolean state, LocalDate date) {
        return ordersRepository.findByOdStateAndOdDate(state, date);
    }


}
