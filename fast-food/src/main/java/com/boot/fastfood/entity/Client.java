package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "clientCode", nullable = false)
    private String clientCode;

    @Column(name = "clientName")
    private String clientName;
}

