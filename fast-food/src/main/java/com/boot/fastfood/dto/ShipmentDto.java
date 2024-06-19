package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Contract;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ShipmentDto {
    private String smCode;

    private Contract contract;

    private LocalDate smSchedule;

}
