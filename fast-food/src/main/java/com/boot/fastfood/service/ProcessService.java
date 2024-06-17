package com.boot.fastfood.service;

import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.boot.fastfood.entity.Process;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    public Process save(AddProcessDTO dto) {
        return processRepository.save(dto.toEntity());
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }
}
