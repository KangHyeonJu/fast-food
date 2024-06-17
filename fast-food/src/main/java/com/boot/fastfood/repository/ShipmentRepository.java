package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Shipment;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ShipmentRepository extends JpaRepository<Shipment, Long>, ShipmentRepositoryCustom {


}
