package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Process.AddProcessDTO;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProcessApiController {

    private final ProcessService processService;

    @PostMapping("/addProcess")
    public ResponseEntity<Process> save(@RequestBody AddProcessDTO dto) {
        Process process = processService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(process);
    }
}
