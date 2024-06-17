package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Clients {
    @Id
    @Column(name = "clCode")
    private String clCode;

    @Column(name = "clName")
    private String clName;

    @Column(name = "clType")
    private String clType;

    @Column(name = "clPhone")
    private String clPhone;

}
