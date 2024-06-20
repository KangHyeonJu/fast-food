package com.boot.fastfood.service;

import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    public List<Materials> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Materials> findByMtCode(String mtCode) {

        return Optional.ofNullable(materialRepository.findByMtCode(mtCode));
    }
}
