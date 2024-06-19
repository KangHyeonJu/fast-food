package com.boot.fastfood.dto.Materials;

import com.boot.fastfood.entity.Materials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialsListDTO {

    private String mtCode;
    private String mtName;

    public MaterialsListDTO(Materials materials) {
        this.mtCode = materials.getMtCode();
        this.mtName = materials.getMtName();
    }

}
