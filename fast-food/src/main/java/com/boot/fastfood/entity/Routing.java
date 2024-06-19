//package com.boot.fastfood.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "routing")
//public class Routing {
//    @EmbeddedId
//    private RoutingId id;
//
//    @ManyToOne
//    @MapsId("itCode")
//    @JoinColumn(name = "itCode")
//    private Items items;
//
//    @ManyToOne
//    @MapsId("pcCode")
//    @JoinColumn(name = "pcCode")
//    private Process process;
//
//    @Column(name = "sequence")
//    private int sequence;
//
//
//}
