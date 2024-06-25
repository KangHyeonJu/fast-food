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
public class MaterialsDTO {

    private String mtCode;
    private String mtName;
    private int mtAmount;
    private String vdCode;
    private String vdName;

    public MaterialsDTO(Materials materials) {
        this.mtCode = materials.getMtCode();
        this.mtName = materials.getMtName();
    }

}
