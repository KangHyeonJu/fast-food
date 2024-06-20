package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.ToOne;
import org.springframework.format.annotation.DateTimeFormat;

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

    //실생산량
    @Column(name = "pNo")
    private int pNo;

    //생산품
    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items itName;

    //생산목표량
    @Column(name = "pmAmount")
    private int pmAmount;
}
