package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Items {
    @Id
    @Column(name = "itCode")
    private String itCode;

    @Column(name = "itName")
    private String itName;

    @Column(name = "itStock")
    private int itStock;

    @Column(name = "itType")
    private String itType;
}
