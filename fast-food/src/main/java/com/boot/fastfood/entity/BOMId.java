package com.boot.fastfood.entity;

import java.io.Serializable;
import java.util.Objects;

public class BOMId implements Serializable {
    private String item;
    private String material;

    // Default constructor
    public BOMId() {}

    // Getters and Setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BOMId bomId = (BOMId) o;
        return item.equals(bomId.item) && material.equals(bomId.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, material);
    }
}
