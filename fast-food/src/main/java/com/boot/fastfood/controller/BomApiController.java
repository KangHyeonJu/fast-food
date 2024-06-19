package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Bom.AddBomDTO;
import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.repository.BomRepository;
import com.boot.fastfood.service.BomService;
import com.boot.fastfood.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/bom/{itCode}")
    public ResponseEntity<?> item(@PathVariable String itCode) {

        List<BOM> bomList = bomService.findByid(itCode);
        ProcessListDTO listDTO = new ProcessListDTO();
        for(BOM bom : bomList) {
            String mtCode = bom.getMaterials().getMtCode();
            Materials materials = materialsService.findById(mtCode);
            ProcessDTO dto = new ProcessDTO();
            dto.setPcCode(materials.getMtCode());
            dto.setPcName(materials.getMtName());
            listDTO.addProcess(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);
    }


}
