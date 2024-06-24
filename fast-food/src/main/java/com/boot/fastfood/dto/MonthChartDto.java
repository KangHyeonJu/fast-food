package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthChartDto {
    private String itName;
    private int month;
    private long totalAmount;

    public MonthChartDto(String itName, int month, long totalAmount) {
        this.itName = itName;
        this.month = month;
        this.totalAmount = totalAmount;
    }

}
