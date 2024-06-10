package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "facility")
public class Facility {
    @Id
    @Column(name = "fcCode", nullable = false)
    private String fcCode;

    @Column(name = "fcName")
    private String fcName;

    @Column(name = "fcStatus", columnDefinition = "TINYINT DEFAULT 0")
    private Integer fcStatus;
}

