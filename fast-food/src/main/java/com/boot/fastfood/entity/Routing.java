//package com.boot.fastfood.entity;
//
//import com.boot.fastfood.repository.RoutingRepository;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "routing")
////@IdClass(RoutingId.class)
//public class Routing {
////    @EmbeddedId
////    private RoutingId id;
//
////    @ManyToOne
////    @MapsId("itCode")
////    @JoinColumn(name = "itCode")
////    private Items items;
////
////    @ManyToOne
////    @MapsId("pcCode")
////    @JoinColumn(name = "pcCode")
////    private Process process;
//
//    @Column(name = "sequence")
//    private int sequence;
//
//
//    @Builder
//    public Routing(Items items, Process process, int sequence) {
//        //this.id.setItems(items);
//        //this.id.setProcess(process);
//        this.id = new RoutingId(items, process);
//        this.sequence = sequence;
//    }
//
//
///*
//    public void setRoutingId(Items items, Process process) {
//        id.setItems(items);
//        id.setProcess(process);
//    }
//
//    @Builder
//    public Routing(RoutingId id, int sequence) {
//        this.id = setRoutingId(id.);
//        this.sequence = sequence;
//    }
//*/
//
//}
//
