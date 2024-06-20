package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "odCode")
    private String odCode;

    @ManyToOne
    @JoinColumn(name = "ctCode")
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "mtCode")
    private Materials materials;

    @Column(name = "odDate")
    private LocalDate odDate;

    @Column(name = "odAmount")
    private int odAmount;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;


}
