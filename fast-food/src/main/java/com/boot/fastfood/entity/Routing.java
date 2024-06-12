package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "routing")
public class Routing {
    @Id
    @Column(name = "rtCode", nullable = false)
    private String rtCode;

    @ManyToOne
    @JoinColumn(name = "itCode", nullable = false)
    private Items item;

    @ManyToOne
    @JoinColumn(name = "pcCode", nullable = false)
    private Process process;

    @Column(name = "sequence")
    private Integer sequence;

}

