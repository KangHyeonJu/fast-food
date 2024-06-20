package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "process")
public class Process {
    @Id
    @Column(name = "pcCode")
    private String pcCode;

    @Column(name = "pcName")
    private String pcName;

    @Column(name = "pcCnt")
    private String pcCnt;

    @ManyToOne
    @JoinColumn(name = "fcCode")
    private Facility facility;

    @Builder
    public Process(String pcCode, String pcName, String pcCnt) {
        this.pcCode = pcCode;
        this.pcName = pcName;
        this.pcCnt = pcCnt;
    }

}
