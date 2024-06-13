package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "contract")
public class Contract {
    @Id
    @Column(name = "ctCode")
    private String ctCode;

    @ManyToOne
    @JoinColumn(name = "clCode")
    private Clients clients;

    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items items;

    @Column(name = "ctAmount")
    private int ctAmount;

    @Column(name = "deliveryPlace")
    private String deliveryPlace;

    @Column(name = "ctDate")
    private Date ctDate;

    @Column(name = "deliveryDate")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

}
