package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.dto.Routing.DeleteRoutingDTO;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.service.ItemService;
import com.boot.fastfood.service.ProcessService;
import com.boot.fastfood.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoutingApiController {
    private final ProcessService processService;
    private final RoutingService routingService;
    private final ItemService itemService;

    @PostMapping("/routing")
    public ResponseEntity<?> saveRouting(@RequestBody AddRoutingDTO dto) {

        List<Routing> routingList = routingService.findByid(dto.getItCode());
        List<Routing> routing;

        if(routingList.isEmpty()) {
            routing = routingService.save(dto);
        } else {
            routingService.delete(dto.getItCode());
            routing = routingService.save(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routing);
    }

    @GetMapping("/routing/{itCode}")
    public ResponseEntity<?> item(@PathVariable("itCode") String itCode) {

        List<Routing> routingList = routingService.findByid(itCode);
        ProcessListDTO listDTO = new ProcessListDTO();
        for(Routing routing : routingList) {
            String pcCode = routing.getProcess().getPcCode();
            Process process = processService.findById(pcCode);
            ProcessDTO dto = new ProcessDTO();
            dto.setPcCode(process.getPcCode());
            dto.setPcName(process.getPcName());
            dto.setPcCnt(process.getPcCnt());
            listDTO.addProcess(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);
    }

    @DeleteMapping("/routing/{itCode}/{pcCode}")
    public ResponseEntity<?> deleteRouting(@PathVariable("itCode") String itCode, @PathVariable("pcCode") String pcCode) {
        routingService.deleteById(itCode, pcCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
