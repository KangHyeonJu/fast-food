package com.boot.fastfood.dto;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Production;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MaterialOrderDto {
    private Contract contract;

    private Items items;

    private Production production;

    private List<BOM> bomList = new ArrayList<>();

    private String[][] bomListAmount = new String[bomList.size()][2];

    private int amount;

}
