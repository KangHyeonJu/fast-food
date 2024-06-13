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
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "emCode")
    private String emCode;

    @Column(name = "emName")
    private String emName;

}
