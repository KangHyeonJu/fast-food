package com.boot.fastfood.dto.Process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.boot.fastfood.entity.Process;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddProcessDTO {

    private String pcCode;
    private String pcName;
    private String pcCnt;

    public Process toEntity() {
        return Process.builder()
                .pcCode(pcCode)
                .pcName(pcName)
                .pcCnt(pcCnt)
                .build();
    }

}
