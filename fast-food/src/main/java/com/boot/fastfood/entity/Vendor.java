package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
}
