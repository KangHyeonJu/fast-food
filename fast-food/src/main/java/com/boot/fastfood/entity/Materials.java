package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "materials")
public class Materials {
    @Id
    @Column(name = "mtCode")
    private String mtCode;

    @Column(name = "mtName")
    private String mtName;

    @Column(name = "mtStock")
    private int mtStock;

    @ManyToOne
    @JoinColumn(name = "vdCode")
    private Vendor vendor;

}
