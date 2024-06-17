package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "production")
public class Production {
    @Id
    @Column(name = "pmCode")
    private String pmCode;

    @ManyToOne
    @JoinColumn(name = "ctCode")
    private Contract contract;

    @Column(name = "pmSDate")
    private LocalDate pmSDate;

    @Column(name = "pmEDate")
    private LocalDate pmEDate;

    @Column(name = "pNo")
    private int pNo;


}
