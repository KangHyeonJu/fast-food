package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "facility")
public class Facility {
    @Id
    @Column(name = "fcCode")
    private String fcCode;

    @Column(name = "fcName")
    private String fcName;

    @Column(name = "fcCapa")
    private String fcCapa;

    @Column(name = "fcPreTime")
    private String fcPreTime;

    @Column(name = "cycleHour")
    private String cycleHour;

    @Column(name = "fcDate")
    private Date fcDate;

    @Column(name = "fcStatus")
    private String fcStatus;

    @ManyToOne
    @JoinColumn(name = "pcCode", nullable = true)
    private Process process;

}
