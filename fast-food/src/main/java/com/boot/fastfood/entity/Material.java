package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "material")
public class Material {
    @Id
    @Column(name = "mtCode", nullable = false)
    private String mtCode;

    @Column(name = "mtName")
    private String mtName;

    @Column(name = "mtStock")
    private Integer mtStock;

}

