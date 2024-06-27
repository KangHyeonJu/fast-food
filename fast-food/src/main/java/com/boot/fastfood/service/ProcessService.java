package com.boot.fastfood.service;

import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.ProcessRepository;
import com.boot.fastfood.repository.WorksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.boot.fastfood.entity.Process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final FacilityService facilityService;
    private final WorksRepository worksRepository;

    public Process save(AddProcessDTO dto) {

        Process process = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        process.setPcCode("PC" + nowTime);

        Facility facility = facilityService.findByFcName(dto.getFcCode());
        process.setFacilities(facility);

        return processRepository.save(process);
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public ProcessListDTO findList() {
        List<Process> process = processRepository.findAll();

        ProcessListDTO processListDTO = new ProcessListDTO();
        for(Process pro : process) {
            ProcessDTO dto = new ProcessDTO();
            dto.setPcName(pro.getPcName());
            dto.setPcCode(pro.getPcCode());
            dto.setPcCnt(pro.getPcCnt());
            dto.setFacility(pro.getFacilities());
            processListDTO.addProcess(dto);
        }
        return processListDTO;
    }

    public List<Process> search(String pcCode, String pcName, String pcCnt) {
        List<Process> process = new ArrayList<>();
        try{
            if(pcCode==null & pcName==null & pcCnt==null || pcCode.isEmpty() & pcName.isEmpty() & pcCnt.isEmpty()) {
                process = processRepository.findAll();
            } else if(!pcCode.isEmpty() & !pcName.isEmpty() & pcCnt.isEmpty()) {
                process = processRepository.findByPcCodeAndPcName(pcCode, pcName);
            } else if(!pcCode.isEmpty() & pcName.isEmpty() & !pcCnt.isEmpty()) {
                process = processRepository.findByPcCodeAndPcCnt(pcCode, pcCnt);
            } else if(pcCode.isEmpty() & !pcName.isEmpty() & !pcCnt.isEmpty()) {
                process = processRepository.findByPcCntAndPcName(pcCnt, pcName);
            } else if(!pcCode.isEmpty() & pcName.isEmpty() & pcCnt.isEmpty()) {
                process = processRepository.findByPcCode(pcCode);
            } else if(pcCode.isEmpty() & !pcName.isEmpty() & pcCnt.isEmpty()) {
                process = processRepository.findByPcName(pcName);
            } else if(pcCode.isEmpty() & pcName.isEmpty() &!pcCnt.isEmpty()) {
                process = processRepository.findByPcCnt(pcCnt);
            } else if(!pcCode.isEmpty() & !pcName.isEmpty() & !pcCnt.isEmpty()) {
                process = processRepository.findByPcCodeAndPcNameAndPcCnt(pcCode, pcName, pcCnt);
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("조회된 결과가 없습니다. ");
        }
        return process;
    }

    public Process findById(String pcCode) {
        return processRepository.findById(pcCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + pcCode));
    }

}
