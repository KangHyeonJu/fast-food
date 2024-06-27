package com.boot.fastfood.entity;

import com.boot.fastfood.repository.RoutingRepository;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "routing")
public class Routing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routing_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
    @JoinColumn(name = "pcCode")
    private Process process;

    @Column(name = "sequence")
    private int sequence;


    @Builder
    public Routing(Items items, Process process, int sequence) {
        this.items = items;
        this.process = process;
        this.sequence = sequence;
    }
/*
    public void setRoutingId(Items items, Process process) {
        id.setItems(items);
        id.setProcess(process);
    }

    @Builder
    public Routing(RoutingId id, int sequence) {
        this.id = setRoutingId(id.);
        this.sequence = sequence;
    }
*/


}

