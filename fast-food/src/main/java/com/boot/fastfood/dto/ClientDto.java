package com.boot.fastfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientDto {
    private String clCode;

    @NotBlank(message = "고객명 입력해주세요.")
    private String clName;

    @NotBlank(message = "고객분류 입력해주세요.")
    private String clType;

    @NotBlank(message = "연락처 입력해주세요.")
    private String clPhone;

}
