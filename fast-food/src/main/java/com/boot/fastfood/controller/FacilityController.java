package com.boot.fastfood.controller;

import com.boot.fastfood.dto.FacilityDto;
import com.boot.fastfood.dto.FacilitySearchDto;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Facility;
import com.boot.fastfood.repository.FacilityRepository;
import com.boot.fastfood.service.FacilityService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
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
public class FacilityController {
    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;
    @GetMapping("/facility")
    public String facilityPage(FacilitySearchDto facilitySearchDto, Model model, FacilityDto facilityDto){
        List<Facility> facility = facilityService.getFacilityList(facilitySearchDto);

        model.addAttribute("facility", facility);
        model.addAttribute("facilitySearchDto", facilitySearchDto);
        model.addAttribute("facilityDto", facilityDto);

        return "facility/facility";
    }

    @PostMapping("/facility/new")
    public String newFacility(@Valid FacilityDto facilityDto, Model model){
        facilityService.saveFacility(facilityDto);


        return "redirect:/facility";
    }

    @PostMapping(value = "/facility/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportContractsToExcel(@RequestParam("fcCode") List<String> fcCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Facility> facilities = facilityRepository.findByFcCodeIn(fcCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contract");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"설비 코드", "설비명", "생산가능량", "생산준비시간", "Cycle hour", "설비입고일", "동작 여부"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Facility facility : facilities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(facility.getFcCode());
            row.createCell(1).setCellValue(facility.getFcName());
            row.createCell(2).setCellValue(facility.getFcCapa());
            row.createCell(3).setCellValue(facility.getFcPreTime());
            row.createCell(4).setCellValue(facility.getCycleHour());

            Cell fcDateCell = row.createCell(5);
            fcDateCell.setCellValue(facility.getFcDate());
            fcDateCell.setCellStyle(dateCellStyle);

            String state = String.valueOf(facility.getFcStatus());
            row.createCell(6).setCellValue(state);


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 열 너비 자동 조정 (옵션)

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=FacilityList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
