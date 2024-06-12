package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "mDelivery")
public class Releases {
    @Id
    @Column(name = "rsCode", nullable = false)
    private String rsCode;

    @ManyToOne
    @JoinColumn(name = "whCode", nullable = false)
    private Warehousing warehousing;

    @Column(name = "rsDate")
    private Date rsDate;

    @Column(name = "rsAmount")
    private Integer rsAmount;

    @ManyToOne
    @JoinColumn(name = "wkCode", nullable = false)
    private Works work;

}

