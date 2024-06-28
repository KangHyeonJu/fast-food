package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Production;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class ProductionDto {
    private String pmCode;
    private String ctCode;
    private LocalDate pmSDate;
    private LocalDate pmEDate;
    private int pNo;
    private String itCode;
    private String itName;
    private int pmAmount;

}
