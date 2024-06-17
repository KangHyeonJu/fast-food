package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "process")
public class Process {
    @Id
    @Column(name = "pcCode")
    private String pcCode;

    @Column(name = "pcName")
    private String pcName;

    @Column(name = "pcCnt")
    private String pcCnt;

    @OneToMany(mappedBy = "fcCode")
    private List<Facility> facilities;


}
