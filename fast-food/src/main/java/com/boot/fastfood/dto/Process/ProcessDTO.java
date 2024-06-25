package com.boot.fastfood.dto.Process;

import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDTO {

    private String pcCode;
    private String pcName;
    private String pcCnt;
    private Facility facility;
    private int pcOutput;

    public ProcessDTO(Process process) {
        this.pcCode = process.getPcCode();
        this.pcName = process.getPcName();
        this.pcCnt = process.getPcCnt();
        this.facility = process.getFacilities();
        this.pcOutput = process.getPcOutput();
    }
}
