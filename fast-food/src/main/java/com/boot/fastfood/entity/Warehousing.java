package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private Date whDate;

    @Column(name = "expirationDate")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

}
