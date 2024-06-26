package com.boot.fastfood.service;

import com.boot.fastfood.dto.Materials.AddMaterialsDTO;
import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.dto.Materials.MaterialsListDTO;
import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MaterialsService {

    private final MaterialsRepository materialsRepository;
    private final VendorService vendorService;

    public List<Materials> findAll() {
        return materialsRepository.findAll();
    }

    public MaterialsListDTO findList() {

        List<Materials> materialsList = materialsRepository.findAll();

        MaterialsListDTO dtoList = new MaterialsListDTO();

        for(Materials materials : materialsList) {
            MaterialsDTO dto = new MaterialsDTO();
            dto.setMtCode(materials.getMtCode());
            dto.setMtName(materials.getMtName());
            dto.setVdCode(materials.getVendor().getVdCode());
            dto.setVdName(materials.getVendor().getVdName());
            dtoList.addProcess(dto);
        }
        return dtoList;
    }

    public Materials findById(String mtCode) {
        return materialsRepository.findById(mtCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + mtCode));
    }

    public Materials save(AddMaterialsDTO dto) {

        Materials materials = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        materials.setMtCode("MT" + nowTime);

        Vendor vendor = vendorService.findByVdCode(dto.getVdCode());
        materials.setVendor(vendor);

        return materialsRepository.save(materials);
    }


}
