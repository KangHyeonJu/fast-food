package com.boot.fastfood.dto.Vendor;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VendorListDTO {

    private final String vdCode;
    private final String vdName;
    private final int alAmount;
    private final List<Materials> materialsList;

    public VendorListDTO(Vendor vendor, List<Materials> materialsList) {
        this.vdCode = vendor.getVdCode();
        this.vdName = vendor.getVdName();
        this.alAmount = vendor.getAlAmount();
        this.materialsList = materialsList;
    }
}
