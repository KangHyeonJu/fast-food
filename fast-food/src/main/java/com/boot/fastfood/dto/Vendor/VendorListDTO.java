package com.boot.fastfood.dto.Vendor;

import com.boot.fastfood.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendorListDTO {

    private final String vdCode;
    private final String vdName;

    public VendorListDTO(Vendor vendor) {
        this.vdCode = vendor.getVdCode();
        this.vdName = vendor.getVdName();
    }
}
