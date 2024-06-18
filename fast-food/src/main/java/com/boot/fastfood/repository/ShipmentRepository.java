package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Shipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ShipmentRepository extends JpaRepository<Shipment, Long>, ShipmentRepositoryCustom {
    List<Shipment> findAllByContract_DeliveryDate(LocalDate today);
}
