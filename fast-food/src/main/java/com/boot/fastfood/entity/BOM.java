package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "bom")
@IdClass(BOMId.class)
public class BOM {
    @Id
    @ManyToOne
    @JoinColumn(name = "itCode", nullable = false)
    private Items item;

    @Id
    @ManyToOne
    @JoinColumn(name = "mtCode", nullable = false)
    private Material material;

    @Column(name = "mtAmount")
    private Integer mtAmount;

}

