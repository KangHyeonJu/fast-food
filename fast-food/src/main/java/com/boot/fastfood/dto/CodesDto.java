package com.boot.fastfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodesDto {
    private Long cNo;
    private String cCode;
    private String cName;
    private boolean cState;
}
