package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "warehousing")
public class Warehousing {
    @Id
    @Column(name = "whCode")
    private String whCode;

    @ManyToOne
    @JoinColumn(name = "odCode")
    private Orders orders;

    @Column(name = "whDate")
    private LocalDate whDate;

    @ManyToOne
    @JoinColumn(name = "emName")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "vdName")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "mtCode")
    private Materials materials;

}
