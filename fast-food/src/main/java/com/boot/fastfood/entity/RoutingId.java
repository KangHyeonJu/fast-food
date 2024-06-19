package com.boot.fastfood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutingId implements Serializable {

    @ManyToOne
//    @MapsId("itCode")
    @JoinColumn(name = "itCode")
    private Items items;

    @ManyToOne
//    @MapsId("pcCode")
    @JoinColumn(name = "pcCode")
    private Process process;


    //equals() and hashCode()
}
