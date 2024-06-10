package com.boot.fastfood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "items")
public class Items {
    @Id
    @Column(name = "itCode", nullable = false, length = 255)
    private String itCode;

    @Column(name = "itName", length = 255)
    private String itName;

    @Column(name = "itStock")
    private Integer itStock;

    @Column(name = "itType", length = 255)
    private String itType;

}

