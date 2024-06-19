package com.boot.fastfood.dto.Process;

import com.boot.fastfood.entity.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessListDTO {

    private final String pcCode;
    private final String pcName;
    private final String pcCnt;

    public ProcessListDTO(Process process) {
        this.pcCode = process.getPcCode();
        this.pcName = process.getPcName();
        this.pcCnt = process.getPcCnt();
    }
}
