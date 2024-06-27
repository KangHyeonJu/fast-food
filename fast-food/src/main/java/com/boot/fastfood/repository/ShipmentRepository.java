package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Shipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ShipmentRepository extends JpaRepository<Shipment, String>, ShipmentRepositoryCustom {
    List<Shipment> findAllByContract_DeliveryDateAndSmStatues(LocalDate today, boolean smStatues);

    Shipment findBySmCode(String smCode);

    List<Shipment> findBySmCodeIn(List<String> smCode);
}
