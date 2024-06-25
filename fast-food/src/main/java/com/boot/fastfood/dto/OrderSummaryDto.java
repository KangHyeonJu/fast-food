package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Materials;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDto {
    private Materials materials;

    private int totalAmount;

    public OrderSummaryDto(Materials materials, int totalAmount) {
        this.materials = materials;
        this.totalAmount = totalAmount;
    }
}
