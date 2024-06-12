package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "process")
public class Process {
    @Id
    @Column(name = "pcCode", nullable = false)
    private String pcCode;

    @Column(name = "pcName")
    private String pcName;

    @Column(name = "pcCnt")
    private String pcCnt;

}

