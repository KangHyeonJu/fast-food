package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "works")
public class Works {

    @Id
    @Column(name = "wkCode", nullable = false)
    private String wkCode;

    @ManyToOne
    @JoinColumn(name = "pmCode", nullable = false)
    private Production production;

    @ManyToOne
    @JoinColumn(name = "pcCode", nullable = false)
    private Process process;

    @Column(name = "wkproduct")
    private String wkproduct;

    @Column(name = "wkNo")
    private Integer wkNo;

    @Column(name = "wkSDate")
    private Date wkSDate;

    @Column(name = "wkEDate")
    private Date wkEDate;

    @Column(name = "sDate", nullable = false)
    private Date sDate;

    @Column(name = "eDate", nullable = false)
    private Date eDate;

    @Column(name = "def", nullable = false)
    private Integer def;

    @Column(name = "defRate", nullable = false)
    private Integer defRate;

}

