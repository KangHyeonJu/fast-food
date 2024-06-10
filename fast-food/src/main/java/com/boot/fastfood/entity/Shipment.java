package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "shipment")
public class Shipment {
    @Id
    @Column(name = "smCode", nullable = false)
    private String smCode;

    @ManyToOne
    @JoinColumn(name = "ctCode", nullable = false)
    private Contract contract;

    @Column(name = "smDate", nullable = false)
    private Date smDate;

    @Column(name = "smStatues", columnDefinition = "TINYINT DEFAULT 0")
    private Integer smStatues;

}
