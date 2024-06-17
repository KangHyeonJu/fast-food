package com.boot.fastfood.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class RoutingId implements Serializable {
    private String itCode;
    private String pcCode;

    //equals() and hashCode()
}
