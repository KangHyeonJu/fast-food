package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private int itEa;

    @Builder
    public Items(String itCode, String itName, String itType) {
        this.itCode = itCode;
        this.itName = itName;
        this.itType = itType;
    }
}
