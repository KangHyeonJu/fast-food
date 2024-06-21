package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Bom.AddBomDTO;
import com.boot.fastfood.dto.Bom.BOMListDTO;
import com.boot.fastfood.dto.Bom.BomDTO;
import com.boot.fastfood.dto.Materials.UpdateMaterialsDTO;
import com.boot.fastfood.entity.BOM;
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
    public ResponseEntity<?> bomInputAdd(@PathVariable String itCode, @PathVariable String mtCode,
                                         @RequestBody UpdateMaterialsDTO mtAmount) {
        BOM bom = bomService.saveById(itCode, mtCode, mtAmount);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bom);
    }

    @GetMapping("/bom/{itCode}")
    public ResponseEntity<?> item(@PathVariable String itCode) {

        List<BOM> bomList = bomService.findByid(itCode);
        BomDTO list = new BomDTO();
        BOMListDTO listDTO = new BOMListDTO();
        for(BOM bom : bomList) {
            list.setMtCode(bom.getMaterials().getMtCode());
            list.setMtName(bom.getMaterials().getMtName());
            list.setMtAmount(bom.getMtAmount());
            listDTO.addBom(list);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);
    }

    @DeleteMapping("/bom/{itCode}/{mtCode}")
    public ResponseEntity<?> deleteRouting(@PathVariable String itCode, @PathVariable String mtCode) {
        bomService.deleteById(itCode, mtCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
