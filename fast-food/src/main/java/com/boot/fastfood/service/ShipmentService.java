package com.boot.fastfood.service;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.Shipment;
import com.boot.fastfood.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    public List<Shipment> getShipmentList(ShipSearchDto shipSearchDto){
        return shipmentRepository.getShipmentList(shipSearchDto);
    }

    public List<Shipment> getshipCompletionList(ShipSearchDto shipSearchDto){
        return shipmentRepository.getshipCompletionList(shipSearchDto);
    }
}
