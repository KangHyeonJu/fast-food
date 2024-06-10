package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "odCode", nullable = false)
    private String odCode;

    @ManyToOne
    @JoinColumn(name = "mtCode", nullable = false)
    private Material material;

    @Column(name = "odDate")
    private Date odDate;

    @Column(name = "odAmount")
    private Integer odAmount;

    @ManyToOne
    @JoinColumn(name = "vdCocd", nullable = false)
    private Vendor vendor;

}

