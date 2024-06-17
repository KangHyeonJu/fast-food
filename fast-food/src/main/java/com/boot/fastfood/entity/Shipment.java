package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "shipment")
public class Shipment {
    @Id
    @Column(name = "smCode")
    private String smCode;

    @ManyToOne
    @JoinColumn(name = "ctCode")
    private Contract contract;

//    private LocalDate smSchedule = contract.getDeliveryDate().minusDays(1);

    @Column(name = "smDate")
    private LocalDate smDate;

    @Column(name = "smStatues", columnDefinition = "TINYINT(0)")
    private boolean smStatues;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

}
