package com.boot.fastfood.dto;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Items;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MaterialOrderDto {
    private String ctCode;

    private Items items;

    private List<BOM> bomList = new ArrayList<>();

    private int amount;


}
