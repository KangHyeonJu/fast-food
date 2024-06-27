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

    //bom 등록
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

    //bom 투입량 등록
    @PutMapping("/bom/{itCode}/{mtCode}")
    public ResponseEntity<?> bomInputAdd(@PathVariable String itCode, @PathVariable String mtCode,
                                         @RequestBody UpdateMaterialsDTO mtAmount) {
        BOM bom = bomService.saveById(itCode, mtCode, mtAmount);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bom);
    }

    //bom 조회
    @GetMapping("/bom/{itCode}")
    public ResponseEntity<?> item(@PathVariable String itCode) {

        List<BOM> bomList = bomService.findByid(itCode);
        MaterialsListDTO listDTO = new MaterialsListDTO();
        for(BOM bom : bomList) {
            String mtCode = bom.getMaterials().getMtCode();
            Materials materials = materialsService.findById(mtCode);
            MaterialsDTO dto = new MaterialsDTO();
            dto.setMtCode(materials.getMtCode());
            dto.setMtName(materials.getMtName());
            dto.setMtAmount(bom.getMtAmount());
            dto.setVdCode(materials.getVendor().getVdCode());
            dto.setVdName(materials.getVendor().getVdName());
            listDTO.addProcess(dto);
        }
        //bom에서 투입량, 코드 조회
        //mt에서 이름 조회

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

    //bom 삭제
    @DeleteMapping("/bom/{itCode}/{mtCode}")
    public ResponseEntity<?> deleteRouting(@PathVariable String itCode, @PathVariable String mtCode) {
        bomService.deleteById(itCode, mtCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
