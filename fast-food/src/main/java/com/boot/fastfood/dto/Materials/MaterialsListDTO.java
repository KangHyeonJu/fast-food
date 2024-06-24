package com.boot.fastfood.dto.Materials;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialsListDTO {

    List<MaterialsDTO> materials = new ArrayList<>();
    //int mtAmount;

    public void addProcess(MaterialsDTO materialsDTO) {
        this.materials.add(materialsDTO);
    }
}
