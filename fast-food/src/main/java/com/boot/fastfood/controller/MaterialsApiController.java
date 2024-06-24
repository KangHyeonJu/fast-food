package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MaterialsApiController {

    private final MaterialsService materialsService;

    @GetMapping("/materials/{mtCode}")
    public ResponseEntity<?> findById(@PathVariable("mtCode") String mtCode) {
        Materials materials = materialsService.findById(mtCode);
        MaterialsDTO dto = new MaterialsDTO();
        dto.setMtCode(materials.getMtCode());
        dto.setMtName(materials.getMtName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }
}
