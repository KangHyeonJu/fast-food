package com.boot.fastfood.dto;

import com.boot.fastfood.constant.FcStatus;
import com.boot.fastfood.entity.Facility;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class FacilityDto {
    private Long fcCode;

    private String fcName;

    private String fcCapa;

    private String fcPreTime;

    private String cycleHour;

    private LocalDate fcDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public Facility addFacility(){
        return modelMapper.map(this, Facility.class);
    }

}
