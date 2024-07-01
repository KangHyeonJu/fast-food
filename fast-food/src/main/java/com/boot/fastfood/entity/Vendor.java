package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vendor")
public class Vendor {
    @Id
    @Column(name = "vdCode")
    private String vdCode;

    @Column(name = "vdName")
    private String vdName;

    @Column(name = "alAmount")
    private Integer alAmount;

    @OneToMany(mappedBy = "vendor")
    private List<Materials> materials;

    @Builder
    public Vendor(String vdCode, String vdName, Integer alAmount) {
        this.vdCode = vdCode;
        this.vdName = vdName;
        this.alAmount = alAmount;
    }
}
