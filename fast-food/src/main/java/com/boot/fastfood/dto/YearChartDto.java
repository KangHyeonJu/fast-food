package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YearChartDto {
    private String itName;
    private int year;
    private int totalAmount;

    public YearChartDto(String itName, int year, int totalAmount) {
        this.itName = itName;
        this.year = year;
        this.totalAmount = totalAmount;
    }
}
