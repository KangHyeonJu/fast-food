package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter @Setter
@Table(name = "bom")
public class BOM {
    @Id
    @Column(name = "bomCode")
    private String bomCode;

    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
    @JoinColumn(name = "mtCode")
    private Materials materials;

    @Column(name = "mtAmount")
    private float mtAmount;
}
