package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "warehousing")
public class Warehousing {
    @Id
    @Column(name = "whCode", nullable = false)
    private String whCode;

    @ManyToOne
    @JoinColumn(name = "odCode", nullable = false)
    private Orders order;

    @Column(name = "whDate")
    private Date whDate;

    @Column(name = "expirationDate")
    private Date expirationDate;

    @Column(name = "whAmount")
    private Integer whAmount;

}

