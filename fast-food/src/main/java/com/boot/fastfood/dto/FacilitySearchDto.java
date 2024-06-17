package com.boot.fastfood.dto;

import com.boot.fastfood.constant.FcStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FacilitySearchDto {
    private FcStatus searchStatus;

    private String fcCode;

    private String fcName;

}
