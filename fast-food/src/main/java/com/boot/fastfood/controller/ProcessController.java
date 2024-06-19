package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

}
