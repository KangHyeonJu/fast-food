package com.boot.fastfood.dto.Process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessFacilityDTO {

    List<ProcessDTO> process = new ArrayList<>();

    public void addProcess(ProcessDTO process) {
        this.process.add(process);
    }
}
