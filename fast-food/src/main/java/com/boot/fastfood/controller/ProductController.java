package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.dto.ProductionSearchDto;
import com.boot.fastfood.dto.Vendor.VendorListDTO;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.repository.ProductionRepository;
import com.boot.fastfood.repository.WorksRepository;
import com.boot.fastfood.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/machine")
@RequiredArgsConstructor
public class ProductController {
    private final ProductionService productionService;
    private final WorksService worksService;
    private final WorksRepository worksRepository;
    private final ProductionRepository productionRepository;
    private final ItemService itemService;
    private final ProcessService processService;

    @GetMapping("/productPlan")
    public String productPlan(Model model) {
        List<ProductionDto> productions = productionService.getAllProductions();
        List<Works> works = worksService.findAll();
        List<Items> items = itemService.findAll();

        model.addAttribute("works", works);
        model.addAttribute("items", items);
        model.addAttribute("productions", productions);
        return "ProductPages/productionPlan";
    }

    @GetMapping("/fetchWorksData")
    @ResponseBody
    public ResponseEntity<List<Works>> fetchWorksData(@RequestParam(name = "pmCode") String pmCode) {

        System.out.println("11111111111111" + pmCode);

        List<Works> worksList = worksService.findByProductionPmCode(pmCode);

//        List<Works> worksList = new ArrayList<>();
//
//        Works works = new Works();
//        works.setDef(123);
//        works.setEDate(LocalDateTime.now());
//
//        worksList.add(works);

        return ResponseEntity.ok(worksList);
    }

    @GetMapping("/productPlan/{pmCode}")
    @ResponseBody
    public ResponseEntity<?> wkList(@PathVariable String pmCode) {
        Production production = productionRepository.findByPmCode(pmCode);
        List<Works> worksList = worksRepository.findByProduction(production);

        return ResponseEntity.status(HttpStatus.OK)
                .body(worksList);
    }

    @GetMapping("/wash")
    public String wash() {
        return "ProductPages/Machine/wash";
    }

    @GetMapping("/extractors")
    public String extractors() {
        return "ProductPages/Machine/extractors";
    }

    @GetMapping("/extractors2")
    public String extractors2() {
        return "ProductPages/Machine/extractors2";
    }

    @GetMapping("/filters")
    public String filters() {
        return "ProductPages/Machine/filters";
    }

    @GetMapping("/sterilizer")
    public String sterilizer() {
        return "ProductPages/Machine/sterilizer";
    }

    @GetMapping("/sterilizer2")
    public String sterilizer2() {
        return "ProductPages/Machine/sterilizer2";
    }

    @GetMapping("/fillings")
    public String fillings() {
        return "ProductPages/Machine/fillings";
    }

    @GetMapping("/frezzer")
    public String frezzer() {
        return "ProductPages/Machine/frezzer";
    }

    @GetMapping("/checker")
    public String checker() {
        return "ProductPages/Machine/checker";
    }

    @GetMapping("/packing")
    public String packing() {
        return "ProductPages/Machine/packing";
    }

    @GetMapping("/endProcess")
    public String endProcess(Model model) {
        List<Items> items = itemService.findAll();
        model.addAttribute("items", items);

        List<Process> processes = processService.findAll();
        model.addAttribute("processes", processes);

        return "ProductPages/endProcess";
    }

    @GetMapping("/Calendar")
    public String Calendar() {
        return "ProductPages/Calendar";
    }

    @PostMapping(value = "/productPlan/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportContractsToExcel(@RequestParam("pmCode") List<String> pmCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Production> productions = productionRepository.findByPmCodeIn(pmCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contract");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"생산 코드", "생산 시작일", "생산 종료일", "생산품", "생산 목표량[Box]"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Production production : productions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(production.getPmCode());

            // 생산 시작일
            Cell startDateCell = row.createCell(1);
            startDateCell.setCellValue(production.getPmSDate());
            startDateCell.setCellStyle(dateCellStyle); // 날짜 형식 적용

            // 생산 종료일
            Cell endDateCell = row.createCell(2);
            endDateCell.setCellValue(production.getPmEDate());
            endDateCell.setCellStyle(dateCellStyle); // 날짜 형식 적용

            // 생산품
            row.createCell(3).setCellValue(production.getItName().getItName());

            // 생산 목표량
            row.createCell(4).setCellValue(production.getPmAmount());

            // 열 너비 자동 조정
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ProductPlanList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/searchProductionPlan")
    public String searchEmployee(
            @RequestParam(required = false, name = "pmCode") String pmCode,
            @RequestParam(required = false, name = "startTime") LocalDate startTime,
            @RequestParam(required = false, name = "itName") String itName,
            Model model) {

        List<ProductionDto> productions = productionService.findAllProductionsWithItems()
                .stream()
                .map(production -> {
                    ProductionDto dto = new ProductionDto();
                    dto.setPmCode(production.getPmCode());
                    dto.setCtCode(production.getContract() != null ? production.getContract().getCtCode() : null);
                    dto.setPmSDate(production.getPmSDate());
                    dto.setPmEDate(production.getPmEDate());
                    dto.setPNo(production.getPNo());
                    dto.setItCode(production.getItName() != null ? production.getItName().getItCode() : null);
                    dto.setItName(production.getItName() != null ? production.getItName().getItName() : null);
                    dto.setPmAmount(production.getPmAmount());
                    return dto;
                })
                .collect(Collectors.toList());

        // 필터링 조건에 따라 조회 처리
        if (pmCode != null && !pmCode.isEmpty()) {
            productions = productions.stream()
                    .filter(dto -> dto.getPmCode() != null && dto.getPmCode().contains(pmCode))
                    .toList();
        }
        if (startTime != null) {
            productions = productions.stream()
                    .filter(dto -> dto.getPmSDate() != null && dto.getPmSDate().equals(startTime))
                    .toList();
        }
        if (itName != null && !itName.isEmpty()) {
            productions = productions.stream()
                    .filter(dto -> dto.getItName() != null && dto.getItName().contains(itName))
                    .toList();
        }
        System.out.println("시간 : " + startTime);

        List<Works> works = worksService.findAll();
        List<Items> items = itemService.findAll();
        model.addAttribute("works", works);
        model.addAttribute("items", items);

        model.addAttribute("productions", productions);

        return "ProductPages/productionPlan";  // HTML 템플릿 파일 이름
    }


}