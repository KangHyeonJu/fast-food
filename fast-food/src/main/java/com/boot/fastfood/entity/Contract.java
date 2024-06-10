package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "contract")
public class Contract {
    @Id
    @Column(name = "ctCode", nullable = false)
    private String ctCode;

    @ManyToOne
    @JoinColumn(name = "clientCode", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "itCode", nullable = false)
    private Items item;

    @Column(name = "ctAmount")
    private Integer ctAmount;

    @Column(name = "deliveryPlace")
    private String deliveryPlace;

    @Column(name = "ctDate")
    private Date ctDate;

    @Column(name = "deliveryDate")
    private Date deliveryDate;

}
