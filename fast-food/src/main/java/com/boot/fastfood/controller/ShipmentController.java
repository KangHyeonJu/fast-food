package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.dto.ShipmentDto;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Shipment;
import com.boot.fastfood.repository.ShipmentRepository;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.ItemsService;
import com.boot.fastfood.service.ShipmentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ItemsService itemsService;
    private final EmployeeService employeeService;
    private final ShipmentRepository shipmentRepository;

    @GetMapping("/shipment")
    public String shipmentPage(ShipSearchDto shipSearchDto, Model model){
        List<Shipment> shipments = shipmentService.getShipmentList(shipSearchDto);
        List<Items> items = itemsService.getAllItems();
        List<Employee> employees = employeeService.getAllEmployees();
        List<Shipment> todayShips = shipmentService.getTodaySthipList();

        List<ShipmentDto> shipmentDtoList = new ArrayList<>();

        for(Shipment shipment : shipments){
            ShipmentDto shipmentDto = new ShipmentDto();
            shipmentDto.setSmCode(shipment.getSmCode());
            shipmentDto.setContract(shipment.getContract());
            shipmentDto.setSmCalendar(shipment.getContract().getDeliveryDate().minusDays(1));
            shipmentDtoList.add(shipmentDto);
        }


        model.addAttribute("shipments", shipmentDtoList);
        model.addAttribute("todayShips", todayShips);
        model.addAttribute("shipSearchDto", shipSearchDto);
        model.addAttribute("items", items);
        model.addAttribute("employees", employees);

        return "shipment/shipment";
    }

    //출하등록
    @PostMapping("/shipment/{smCode}/{emName}")
    public String shipRegistration(@PathVariable("smCode") String smCode, @PathVariable("emName") String emName){
        try {
            shipmentService.shipRegistration(smCode, emName);
            return "redirect:/shipment";
        }catch (Exception e){
            return "redirect:/shipment";
        }
    }

    @GetMapping("/shipCompletion")
    public String shipCompletionPage(ShipSearchDto shipSearchDto, Model model){
        List<Shipment> shipments = shipmentService.getshipCompletionList(shipSearchDto);
        List<Items> items = itemsService.getAllItems();

        model.addAttribute("shipments", shipments);
        model.addAttribute("shipSearchDto", shipSearchDto);
        model.addAttribute("items", items);

        return "shipment/shipCompletion";
    }
    @PostMapping(value = "/shipment/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportshipmentToExcel(@RequestParam("smCode") List<String> smCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Shipment> shipments = shipmentRepository.findBySmCodeIn(smCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Shipment");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"출하 코드", "수주 코드", "고객명", "제품명", "출하량", "출하예정일"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        for (Shipment shipment : shipments) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(shipment.getSmCode());
            row.createCell(1).setCellValue(shipment.getContract().getCtCode());
            row.createCell(2).setCellValue(shipment.getEmployee().getEmName());
            row.createCell(3).setCellValue(shipment.getContract().getItems().getItName());
            row.createCell(4).setCellValue(shipment.getContract().getCtAmount());
            Cell dateCell = row.createCell(5);
            dateCell.setCellValue(shipment.getSmDate());
            dateCell.setCellStyle(dateCellStyle);


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ShipmentList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    @PostMapping(value = "/shipmentC/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportshipmentCToExcel(@RequestParam("smCode") List<String> smCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Shipment> shipments = shipmentRepository.findBySmCodeIn(smCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Shipment");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"출하 코드", "수주 코드", "고객명", "제품명", "출하량", "출하예정일"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        for (Shipment shipment : shipments) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(shipment.getSmCode());
            row.createCell(1).setCellValue(shipment.getContract().getCtCode());
            row.createCell(2).setCellValue(shipment.getEmployee().getEmName());
            row.createCell(3).setCellValue(shipment.getContract().getItems().getItName());
            row.createCell(4).setCellValue(shipment.getContract().getCtAmount());
            Cell dateCell = row.createCell(5);
            dateCell.setCellValue(shipment.getSmDate());
            dateCell.setCellStyle(dateCellStyle);


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ShipmentCList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
