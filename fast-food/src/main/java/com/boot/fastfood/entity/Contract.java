package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
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
    @JoinColumn(name = "clName")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "emName")
    private Employee employee;

    @OneToMany(mappedBy = "contract")
    private List<Production> productions;

    @Column(name = "ctStatus")
    private String ctStatus; // 수주 상태 필드새로 추가


}
