package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddItemDTO {

    private String itCode;
    private String itName;
    private String itType;

    public Items toEntity() {
        return Items.builder()
                .itCode(itCode)
                .itName(itName)
                .itType(itType)
                .build();
    }
}
