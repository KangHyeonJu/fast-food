package com.boot.fastfood.service;

import com.boot.fastfood.dto.Materials.AddMaterialsDTO;
import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public Materials save(AddMaterialsDTO dto) {

        Materials materials = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        materials.setMtCode("MT" + nowTime);

        return materialsRepository.save(materials);
    }


}
