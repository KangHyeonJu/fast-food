package com.boot.fastfood.repository;

import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.entity.QOrders;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrdersRepositoryCustomImpl implements OrdersRepositoryCustom{
    private final JPAQueryFactory query;

    private final QOrders orders = QOrders.orders;

    private BooleanExpression searchByCtCode(String ctCode){
        if(ctCode == null){
            return null;
        }
        return orders.contract.ctCode.like("%" + ctCode + "%");
    }

    private BooleanExpression searchByOdCode(String odCode){
        if(odCode == null){
            return null;
        }
        return orders.odCode.like("%" + odCode + "%");
    }

    private BooleanExpression searchByMtCode(String mtCode){
        if(mtCode == null){
            return null;
        }
        return orders.materials.mtCode.like("%" + mtCode + "%");
    }

    private BooleanExpression searchByEmCode(String emCode){
        if(emCode == null){
            return null;
        }
        return orders.employee.emCode.like("%" + emCode + "%");
    }



    @Override
    public List<Orders> getOrderList(OrderSearchDto orderSearchDto) {
        return query
                .selectFrom(orders)
                .where(searchByCtCode(orderSearchDto.getCtCode()), searchByOdCode(orderSearchDto.getOdCode()), searchByMtCode(orderSearchDto.getMtCode()), searchByEmCode(orderSearchDto.getEmCode()))
                .fetch();
    }
}
