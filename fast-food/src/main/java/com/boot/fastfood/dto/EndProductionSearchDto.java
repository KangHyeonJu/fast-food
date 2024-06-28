package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Items;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EndProductionSearchDto {
    private String pmCode;

    private Contract contract;

    private LocalDate pmEDate;

    private Items items;

    private int pmAmount;
}
