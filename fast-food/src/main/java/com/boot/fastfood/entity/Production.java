package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.ToOne;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pmSDate;

    @Column(name = "pmEDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pmEDate;

    //실생산량
    @Column(name = "pNo")
    private int pNo;

    //생산품
    @ManyToOne
    @JoinColumn(name = "itCode", referencedColumnName = "itCode")
    private Items itCode;

    //생산목표량
    @Column(name = "ctAmount")
    private int ctAmount;
}
