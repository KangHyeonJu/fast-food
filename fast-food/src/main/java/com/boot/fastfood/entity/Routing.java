package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Embeddable
@Table(name = "routing")
//@IdClass(RoutingId.class)
public class Routing {
//    @EmbeddedId
//    private RoutingId id;

    @ManyToOne
    @Id
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
    @Id
    @JoinColumn(name = "pcCode")
    private Process process;

    @Column(name = "sequence")
    private int sequence;


}

