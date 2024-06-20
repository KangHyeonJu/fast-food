package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Materials;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderTodayDto {
    private Contract contract;

    private Materials materials;

    private int orderAmount;

}
