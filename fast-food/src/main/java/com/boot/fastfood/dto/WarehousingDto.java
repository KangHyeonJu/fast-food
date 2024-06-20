package com.boot.fastfood.dto;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Orders;
import com.boot.fastfood.entity.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class WarehousingDto {
    private String whCode;
    private String odCode;
    private LocalDate whDate;
    private LocalDate expirationDate;
    private int odAmount;
    private String vdCode;
    private String mtCode;
    private String emCode;
    private LocalDate odDueDate;


}
