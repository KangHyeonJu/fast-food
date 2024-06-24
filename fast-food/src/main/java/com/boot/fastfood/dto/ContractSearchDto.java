package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ContractSearchDto {

    private String ctCode;
    private String clName;
    private LocalDate ctDate;
    private String itName;
    private String emName;

    public ContractSearchDto(String ctCode, String clName, LocalDate ctDate, String itName, String emName) {
        this.ctCode = ctCode;
        this.clName = clName;
        this.ctDate = ctDate;
        this.itName = itName;
        this.emName = emName;
    }

}
