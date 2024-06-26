package com.boot.fastfood.dto.Materials;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMaterialsDTO {

    private String mtName;
    private int mtMin;
    private int mtMax;
    private String vdCode;
    private int leadTime;

    public Materials toEntity() {
        return Materials.builder()
                .mtName(mtName)
                .mtMax(mtMax)
                .mtMin(mtMin)
                .leadTime(leadTime)
                .build();
    }
}
