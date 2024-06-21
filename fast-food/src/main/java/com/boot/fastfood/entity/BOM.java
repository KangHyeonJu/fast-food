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
    @Column(name="bom_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
    @JoinColumn(name = "mtCode")
    private Materials materials;

    @Column(name = "mtAmount")
    private int mtAmount;

    public void update(int mtAmount) {
        this.mtAmount = mtAmount;
    }

}

