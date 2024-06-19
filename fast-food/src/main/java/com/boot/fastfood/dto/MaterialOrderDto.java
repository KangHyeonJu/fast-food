package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Items;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaterialOrderDto {
    private String ctCode;

    private int ctAmount;

    private Items items;


}
