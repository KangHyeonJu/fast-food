package com.boot.fastfood.dto.Item;

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

    private String itName;
    private String itType;

    public Items toEntity() {
        return Items.builder()
                .itName(itName)
                .itType(itType)
                .build();
    }
}
