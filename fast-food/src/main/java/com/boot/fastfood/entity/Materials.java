package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


    @Builder
    public Materials(String mtCode, String mtName) {
        this.mtCode = mtCode;
        this.mtName = mtName;
    }
}
