package com.boot.fastfood.service;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MaterialsService {

    private final MaterialsRepository materialsRepository;

    public List<Materials> findAll() {
        return materialsRepository.findAll();
    }

    public Materials findById(String mtCode) {
        return materialsRepository.findById(mtCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + mtCode));
    }
}
