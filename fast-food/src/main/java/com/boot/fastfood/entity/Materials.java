package com.boot.fastfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vdCode")
    private Vendor vendor;

    private int mtMin;

    private int mtMax;

}
