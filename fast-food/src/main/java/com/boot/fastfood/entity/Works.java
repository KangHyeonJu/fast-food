package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "works")
public class Works {
    @Id
    @Column(name = "wkCode")
    private String wkCode;

    @ManyToOne
    @JoinColumn(name = "pmCode")
    private Production production;

    @Column(name = "wkNo")
    private int wkNo;

    @Column(name = "sDate")
    private Date sDate;

    @Column(name = "eDate")
    private Date eDate;

    @Column(name = "def")
    private int def;

    @Column(name = "defRate")
    private int defRate;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

    @OneToMany(mappedBy = "pcCode")
    private List<Process> process;

}
