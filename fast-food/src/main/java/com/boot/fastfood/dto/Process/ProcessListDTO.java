package com.boot.fastfood.dto.Process;

import com.boot.fastfood.entity.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessListDTO {

    List<ProcessDTO> process = new ArrayList<>();

    public void addProcess(ProcessDTO process) {
        this.process.add(process);
    }

    /*
    public ProcessListDTO(Process process) {
        this.pcCode = process.getPcCode();
        this.pcName = process.getPcName();
        this.pcCnt = process.getPcCnt();
    }

     */
}
