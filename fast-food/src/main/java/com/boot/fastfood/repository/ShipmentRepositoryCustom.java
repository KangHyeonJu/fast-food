package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.Shipment;

import java.util.List;

public interface ShipmentRepositoryCustom {
    List<Shipment> getshipCompletionList(ShipSearchDto shipSearchDto);

    List<Shipment> getShipmentList(ShipSearchDto shipSearchDto);

}
