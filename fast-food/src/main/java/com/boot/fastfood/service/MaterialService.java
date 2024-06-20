package com.boot.fastfood.service;

import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    public List<Materials> findAll() {
        return materialRepository.findAll();
    }
}
