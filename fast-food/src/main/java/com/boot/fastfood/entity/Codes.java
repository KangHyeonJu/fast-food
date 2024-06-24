package com.boot.fastfood.entity;

import com.boot.fastfood.dto.CodesDto;
import com.boot.fastfood.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "codes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Codes {
    @Id
    @Column(name = "cNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cNo;

    @Column(name = "cCode")
    private String cCode;

    @Column(name = "cName")
    private String cName;

    @Column(name = "cState", columnDefinition = "TINYINT(0)")
    private boolean cState;
    public static Codes createCode(CodesDto codesDto){
        return Codes.builder()
                .cCode(codesDto.getCCode())
                .cName(codesDto.getCName())
                .cState(false)
                .build();
    }

}
