package com.boot.fastfood.controller;

import com.boot.fastfood.dto.CodesDto;
import com.boot.fastfood.entity.Codes;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.CodesRepository;
import com.boot.fastfood.service.CodeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.apache.bcel.classfile.Code;
import org.codehaus.groovy.tools.shell.IO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;
    private final CodesRepository codesRepository;

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

    @PostMapping(value = "/code/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportCodeToExcel(@RequestParam("cNo") List<Long> cNo, HttpServletResponse response) throws IOException{
        List<Codes> codes = codesRepository.findBycNoIn(cNo);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Codes");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"번호", "코드", "코드명"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Codes code : codes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(code.getCNo());
            row.createCell(1).setCellValue(code.getCCode());
            row.createCell(2).setCellValue(code.getCName());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=CodeList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
