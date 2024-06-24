package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Materials.AddMaterialsDTO;
import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MaterialsApiController {

    private final MaterialsService materialsService;

    @GetMapping("/materials/{mtCode}")
    public ResponseEntity<?> findById(@PathVariable String mtCode) {
        Materials materials = materialsService.findById(mtCode);
        MaterialsDTO dto = new MaterialsDTO();
        dto.setMtCode(materials.getMtCode());
        dto.setMtName(materials.getMtName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/addMaterials")
    public ResponseEntity<Materials> save(@RequestBody AddMaterialsDTO dto) {
        Materials materials = materialsService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(materials);
    }
}
