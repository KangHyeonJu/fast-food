package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Vendor.VendorListDTO;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.MaterialsRepository;
import com.boot.fastfood.repository.VendorRepository;
import com.boot.fastfood.service.VendorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;
    private final VendorRepository vendorRepository;

    @GetMapping("/vendor")
    public String findAll(Model model) {
        List<VendorListDTO> vendorList = vendorService.findAllVendorsWithMaterials()
                .stream()
                .map(vendor -> new VendorListDTO(vendor, vendor.getMaterials()))
                .toList();

        model.addAttribute("vendorList", vendorList);

        return "system/vendor";
    }


    @PostMapping(value = "/vendor/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportCodeToExcel(@RequestParam(name = "vdCode", required = false) List<String> vdCode, HttpServletResponse response) throws IOException {
        List<Vendor> vendors = vendorRepository.findByVdCodeIn(vdCode);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Codes");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"업체코드", "업체명" , "자재명","총 주문량"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Vendor vendor : vendors) {
            for (Materials material : vendor.getMaterials()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vendor.getVdCode());
            row.createCell(1).setCellValue(vendor.getVdName());
            row.createCell(2).setCellValue(material.getMtName());
            row.createCell(3).setCellValue(vendor.getAlAmount());
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=VendorList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
