package com.boot.fastfood.dto.Process;

import com.boot.fastfood.entity.Facility;
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

    private String pcName;
    private String pcCnt;
    private String fcName;
    private int pcOutput;

    public Process toEntity() {
        return Process.builder()
                .pcName(pcName)
                .pcCnt(pcCnt)
                .pcOutput(pcOutput)
                .build();
    }

}
