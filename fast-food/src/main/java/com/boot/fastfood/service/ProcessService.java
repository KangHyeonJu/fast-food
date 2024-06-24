package com.boot.fastfood.service;

import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.boot.fastfood.entity.Process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final FacilityService facilityService;

    public Process save(AddProcessDTO dto) {

        Process process = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        process.setPcCode("PC" + nowTime);

        Facility facility = facilityService.findByFcCode(dto.getFcCode());
        process.setFacilities(facility);

        return processRepository.save(process);
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public Process findById(String pcCode) {
        return processRepository.findById(pcCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + pcCode));
    }
}
