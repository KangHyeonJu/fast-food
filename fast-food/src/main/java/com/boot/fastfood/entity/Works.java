package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "works")
public class Works {
    //공정 코드
    @Id
    @Column(name = "wkCode")
    private String wkCode;

    //수주 코드
    @ManyToOne
    @JoinColumn(name = "pmCode")
    private Production production;

    //공정 투입량
    @Column(name = "wkInput")
    private int wkInput;

    //공정 배출량
    @Column(name = "wkOutput")
    private int wkOutput;

    //공정 시작시간
    @Column(name = "sDate")
    private LocalDateTime sDate;

    //공정 종료시간
    @Column(name = "eDate")
    private LocalDateTime eDate;

    //불량수
    @Column(name = "def")
    private int def;

    //불량률
    @Column(name = "defRate")
    private int defRate;

    //직원 번호
    @ManyToOne
    @JoinColumn(name = "emCode")
    private Employee employee;

    //공정 코드
    @OneToMany(mappedBy = "pcCode")
    private List<Process> process;
}
