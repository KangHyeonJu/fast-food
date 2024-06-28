package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ProductionSearchDto {
    private String pmCode;
    private LocalDate pmSDate;
    private LocalDate pmEDate;
    private String itName;

    public ProductionSearchDto(String pmCode, LocalDate pmSDate, LocalDate pmEDate, String itName) {
        this.pmCode = pmCode;
        this.pmSDate = pmSDate;
        this.pmEDate = pmEDate;
        this.itName = itName;

    }
}
