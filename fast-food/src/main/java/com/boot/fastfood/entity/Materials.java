package com.boot.fastfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vdCode")
    private Vendor vendor;

    private int mtMin;

    private int mtMax;

    private int leadTime;

    @Builder
    public Materials(String mtCode, String mtName, int mtMax, int mtMin, int leadTime) {
        this.mtCode = mtCode;
        this.mtName = mtName;
        this.mtMax = mtMax;
        this.mtMin = mtMin;
        this.leadTime = leadTime;
    }

}
