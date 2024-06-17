package com.boot.fastfood.entity;

import com.boot.fastfood.constant.FcStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate fcDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "fcStatus")
    private FcStatus fcStatus;

}
