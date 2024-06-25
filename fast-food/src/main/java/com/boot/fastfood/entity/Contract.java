package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "contract")
public class Contract {

    @Id
    @Column(name = "ctCode")
    private String ctCode;

    @ManyToOne
    @JoinColumn(name = "clCode")
    private Clients clients;

    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items items;

    @Column(name = "ctAmount")
    private int ctAmount;

    @Column(name = "deliveryPlace")
    private String deliveryPlace;

    @Column(name = "ctDate")
    private LocalDate ctDate;

    @Column(name = "deliveryDate")
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

    @OneToMany(mappedBy = "contract")
    private List<Production> productions;

    @Column(name = "ctStatus")
    private String ctStatus; // 수주 상태 필드새로 추가

}
