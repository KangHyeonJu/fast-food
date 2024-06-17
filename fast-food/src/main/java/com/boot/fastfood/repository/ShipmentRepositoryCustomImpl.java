package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.QShipment;
import com.boot.fastfood.entity.Shipment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShipmentRepositoryCustomImpl implements ShipmentRepositoryCustom{
    private final JPAQueryFactory query;
    private final QShipment shipment = QShipment.shipment;

    private BooleanExpression searchBySmCode(String smCode){
        if(smCode == null){
            return null;
        }
        return shipment.smCode.like("%" + smCode + "%");
    }

    private BooleanExpression searchByCtCode(String ctCode){
        if(ctCode == null){
            return null;
        }
        return shipment.contract.ctCode.like("%" + ctCode + "%");
    }

    private BooleanExpression searchByClName(String clName){
        if(clName == null){
            return null;
        }
        return shipment.contract.clients.clName.like("%" + clName + "%");
    }

    private BooleanExpression searchByItName(String itName){
        if(itName == null){
            return null;
        }
        return shipment.contract.items.itName.like(itName);
    }

    @Override
    public List<Shipment> getShipmentList(ShipSearchDto shipSearchDto){
        return query
                .selectFrom(shipment)
                .where(searchBySmCode(shipSearchDto.getSmCode()), searchByCtCode(shipSearchDto.getCtCode()), searchByItName(shipSearchDto.getItName()), searchByClName(shipSearchDto.getClName()) ,shipment.smStatues.isFalse())
                .fetch();
    }

    @Override
    public List<Shipment> getshipCompletionList(ShipSearchDto shipSearchDto){
        return query
                .selectFrom(shipment)
                .where(searchBySmCode(shipSearchDto.getSmCode()), searchByCtCode(shipSearchDto.getCtCode()), searchByItName(shipSearchDto.getItName()), searchByClName(shipSearchDto.getClName()) ,shipment.smStatues.isTrue())
                .fetch();
    }
}
