package com.boot.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "calendar")
@NoArgsConstructor
public class Calendar {
    @Id
    @Column(name = "title")
    private String title;

    @Column(name = "sDate")
    private LocalDate sDate;

    @Column(name = "eDate")
    private LocalDate eDate;

    public Calendar(String title, LocalDate sDate , LocalDate eDate) {
        this.title = title;
        this.sDate = sDate;
        this.eDate = eDate;
    }
}
