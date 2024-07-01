package com.boot.fastfood.dto.Vendor;

import com.boot.fastfood.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddVendorDTO {
    private String vdName;
    private String vdCode;
    private Integer alAmount;

    public Vendor toEntity() {
        return Vendor.builder()
                .vdCode(vdCode)
                        .vdName(vdName)
                .alAmount(alAmount)
                .build();
    }
}
