package com.boot.fastfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDto {
    private String emCode;

    @NotBlank(message = "직원 이름을 입력하세요.")
    private String emName;

    private int emCon;
}
