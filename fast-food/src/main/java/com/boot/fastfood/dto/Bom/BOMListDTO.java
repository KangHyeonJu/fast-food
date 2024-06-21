package com.boot.fastfood.dto.Bom;

import com.boot.fastfood.dto.Materials.MaterialsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BOMListDTO {

    List<BomDTO> bom = new ArrayList<>();

    public void addBom(BomDTO dto) {
        this.bom.add(dto);
    }
}
