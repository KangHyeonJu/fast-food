package com.boot.fastfood.controller;

import com.boot.fastfood.dto.CodesDto;
import com.boot.fastfood.entity.Codes;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/code")
    public String codePage(Model model){
        List<Codes> codes = codeService.getAllCodes();
        model.addAttribute("codes", codes);
        return "system/code";
    }

    @PostMapping("/code/new")
    public String newCode(CodesDto codesDto, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "system/code";
        }
        try {
            Codes codes = Codes.createCode(codesDto);
            codeService.saveCode(codes);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "system/code";
        }

        return "redirect:/code";
    }

    @PostMapping("/code/delete/{codeId}")
    public String deleteCode(@PathVariable Long codeId){
        Codes codes = codeService.findByCNo(codeId);
        codes.setCState(true);

        codeService.saveCode(codes);

        return "redirect:/code";
    }


}
