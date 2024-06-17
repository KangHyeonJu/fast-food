package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Items;
import lombok.Getter;

@Getter
public class ItemListDTO {

    private final String itCode;
    private final String itName;
    private final String itType;

    public ItemListDTO(Items items) {
        this.itCode = items.getItCode();
        this.itName = items.getItName();
        this.itType = items.getItType();
    }
}
