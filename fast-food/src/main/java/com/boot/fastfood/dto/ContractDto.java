package com.boot.fastfood.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ContractDto {
    private String ctCode;
    private String clName;
    private String itName;
    private int ctAmount;
    private String deliveryPlace;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ctDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;
    private String emName;
    private String ctStatus;

}
