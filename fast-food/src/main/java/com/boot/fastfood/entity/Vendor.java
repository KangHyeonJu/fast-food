package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "vendor")
public class Vendor {
    @Id
    @Column(name = "vdCode", nullable = false)
    private String vdCode;

    @Column(name = "vdName")
    private String vdName;

}

