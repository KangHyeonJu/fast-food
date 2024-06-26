package com.boot.fastfood.dto.Bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BomDTO {

    private String mtCode;
    private String mtName;
    private float mtAmount;
}
