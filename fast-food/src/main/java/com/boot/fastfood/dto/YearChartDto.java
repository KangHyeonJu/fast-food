package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YearChartDto {
    private String itName;
    private int year;
    private long totalAmount;

    public YearChartDto(String itName, int year, long totalAmount) {
        this.itName = itName;
        this.year = year;
        this.totalAmount = totalAmount;
    }
}
