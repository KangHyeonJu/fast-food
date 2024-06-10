package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "production")
public class Production {
    @Id
    @Column(name = "pmCode", nullable = false)
    private String pmCode;

    @Column(name = "pmSDate", nullable = false)
    private Date pmSDate;

    @Column(name = "pmEDate", nullable = false)
    private Date pmEDate;

    @Column(name = "pName")
    private String pName;

    @Column(name = "product")
    private String product;

    @Column(name = "pNo")
    private Integer pNo;

}

