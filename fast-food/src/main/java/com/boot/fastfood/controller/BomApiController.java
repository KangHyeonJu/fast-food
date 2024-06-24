package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Bom.AddBomDTO;
import com.boot.fastfood.dto.Bom.BOMListDTO;
import com.boot.fastfood.dto.Bom.BomDTO;
import com.boot.fastfood.dto.Materials.MaterialsDTO;
import com.boot.fastfood.dto.Materials.MaterialsListDTO;
import com.boot.fastfood.dto.Materials.UpdateMaterialsDTO;
import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.service.BomService;
import com.boot.fastfood.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BomApiController {

    private final BomService bomService;
    private final MaterialsService materialsService;

    @PostMapping("/bom")
    public ResponseEntity<?> saveRouting(@RequestBody AddBomDTO dto) {

        List<BOM> bomList = bomService.findByid(dto.getItCode());
        List<BOM> bom;

        if(bomList.isEmpty()) {
            bom = bomService.save(dto);
        } else {
            bomService.delete(dto.getItCode());
            bom = bomService.save(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bom);
    }

    @PutMapping("/bom/{itCode}/{mtCode}")
    public ResponseEntity<?> bomInputAdd(@PathVariable("itCode") String itCode, @PathVariable("mtCode") String mtCode,
                                         @RequestBody UpdateMaterialsDTO mtAmount) {
        BOM bom = bomService.saveById(itCode, mtCode, mtAmount);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bom);
    }

    @GetMapping("/bom/{itCode}")
    public ResponseEntity<?> item(@PathVariable("itCode") String itCode) {


        List<BOM> bomList = bomService.findByid(itCode);
        MaterialsListDTO listDTO = new MaterialsListDTO();
        for(BOM bom : bomList) {
            String mtCode = bom.getMaterials().getMtCode();
            Materials materials = materialsService.findById(mtCode);
            MaterialsDTO dto = new MaterialsDTO();
            dto.setMtCode(materials.getMtCode());
            dto.setMtName(materials.getMtName());
            listDTO.addProcess(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);

         /*
        List<BOM> bomList = bomService.findByid(itCode);
        BOMListDTO listDTO = new BOMListDTO();
        for(BOM bom : bomList) {
            String mtCode = bom.getMaterials().getMtCode();
            Materials materials = materialsService.findById(mtCode);
            MaterialsDTO dto = new MaterialsDTO();
            dto.setMtCode(materials.getMtCode());
            dto.setMtName(materials.getMtName());

            listDTO.addProcess(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);*/
    }

    @DeleteMapping("/bom/{itCode}/{mtCode}")
    public ResponseEntity<?> deleteRouting(@PathVariable("itCode") String itCode, @PathVariable("mtCode") String mtCode) {
        bomService.deleteById(itCode, mtCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
