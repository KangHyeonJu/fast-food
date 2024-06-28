package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.EmployeeRepository;
import com.boot.fastfood.repository.ItemsRepository;
import com.boot.fastfood.repository.WorksRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.WorksService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/works")
public class WorksController {
    private final WorksService worksService;
    private final WorksRepository worksRepository;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final ItemsRepository itemsRepository;


    @GetMapping("/workList")
    public String workList(Model model){
        // 오늘 날짜 추가
        LocalDate today = LocalDate.now();

        //공정 불러옴
        List<Works> worksList = worksRepository.findAll();

        List<Works> worksToday = new ArrayList<>();
        for(Works works : worksList){
            if((works.getSDate().toLocalDate().equals(today)) || (works.getEDate().toLocalDate().equals(today))){
                worksToday.add(works);
            }
        }

        model.addAttribute("works", worksToday);

        //작업자 불러옴
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        List<Items> items = itemsRepository.findAll();
        model.addAttribute("items", items);

        return "ProductPages/workList";
    }

    @PostMapping("/addRSDate")
    @ResponseBody
    public void saveRSDate(@RequestBody Map<String, Object> data) {
        String wkCode = (String) data.get("wkCode");
        String emName = (String) data.get("emName");

        Works works = worksRepository.findByWkCode(wkCode);
        Employee employee = employeeRepository.findByEmName(emName);

        Items items = works.getProduction().getItName();
        String itName = items.getItName();

        worksService.setFCWork(wkCode, itName);

        worksService.saveRSDate(works, employee);
    }

    @PostMapping("/addREDate")
    @ResponseBody
    public void saveREDate(@RequestBody Map<String, Object> data) {
        String wkCode = (String) data.get("wkCode");
        Works works = worksRepository.findByWkCode(wkCode);

        Items items = works.getProduction().getItName();
        String itName = items.getItName();

        worksService.setFCStop(wkCode, itName);

        worksService.saveREDate(works);
    }

    @GetMapping("/searchWorkList")
    public String searchWorkList(
            @RequestParam(required = false, name = "wkCode") String wkCode,
            @RequestParam(required = false, name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            Model model) {

        List<Works> works = worksRepository.findAll();

        // 필터링 조건에 따라 조회 처리
        if (wkCode != null && !wkCode.isEmpty()) {
            works = works.stream()
                    .filter(wh -> wh.getWkCode().contains(wkCode))
                    .collect(Collectors.toList());
        }
        if (startTime != null) {
            works = works.stream()
                    .filter(wh -> wh.getSDate() != null && wh.getSDate().toLocalDate().equals(startTime))
                    .collect(Collectors.toList());
        }

        model.addAttribute("works", works);


        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "ProductPages/workList";  // HTML 템플릿 파일 이름
    }

    @PostMapping(value = "/workList/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportCodeToExcel(@RequestParam(name = "wkCode", required = false) List<String> wkCode, HttpServletResponse response) throws IOException {
        List<Works> works = worksRepository.findByWkCodeIn(wkCode);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Codes");

        String[] headers = {"w작업 지시 코드", "작업 투입량", "작업 완료량", "작업 시작 일시", "작업 종료 일시"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        int rowNum = 1;
        for (Works work : works) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(work.getWkCode());
            row.createCell(1).setCellValue(work.getWkInput());
            row.createCell(2).setCellValue(work.getWkOutput());
            // Convert LocalDateTime to a formatted string before setting in the cell
            row.createCell(3).setCellValue(work.getSDate().format(formatter));
            row.createCell(4).setCellValue(work.getEDate().format(formatter));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=WorkList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

