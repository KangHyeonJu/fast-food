package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "releases")
public class Releases {
    @Id
    @Column(name = "rsCode")
    private String rsCode;

    @ManyToOne
    @JoinColumn(name = "whCode")
    private Warehousing warehousing;

    @Column(name = "rsDate")
    private LocalDate rsDate;

    @Column(name = "rsAmount")
    private int rsAmount;

    @ManyToOne
    @JoinColumn(name = "wkCode")
    private Works works;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "mtCode")
    private Materials materials;


}
