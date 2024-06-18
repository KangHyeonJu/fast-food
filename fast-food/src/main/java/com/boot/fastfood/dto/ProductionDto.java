package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ProductionDto {
    private String pmCode;
    private String ctCode;
    private Date pmSDate;
    private Date pmEDate;
    private int pNo;
    private String itCode;
    private String itName;
    private int ctAmount;
}
