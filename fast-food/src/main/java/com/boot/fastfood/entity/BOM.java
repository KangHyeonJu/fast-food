package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter @Setter
@Table(name = "bom")
public class BOM {
    @EmbeddedId
    private BOMId id;

    @ManyToOne
    @MapsId("itCode")
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
    @MapsId("mtCode")
    @JoinColumn(name = "mtCode")
    private Materials materials;

    @Column(name = "mtAmount")
    private int mtAmount;

}

