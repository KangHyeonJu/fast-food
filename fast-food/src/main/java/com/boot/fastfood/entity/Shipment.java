package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "smDate")
    private Date smDate;

    @Column(name = "smStatues")
    private int smStatues;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

}
