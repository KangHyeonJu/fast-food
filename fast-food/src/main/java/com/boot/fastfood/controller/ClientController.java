package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ClientDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.ClientRepository;
import com.boot.fastfood.service.ClientService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @GetMapping("/client")
    public String clientPage(Model model){
        List<Clients> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "system/client";
    }

    @PostMapping("/client/new")
    public String newClient(ClientDto clientDto, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "system/client";
        }
        try {
            Clients clients = Clients.createClient(clientDto);
            clientService.saveClient(clients);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "system/client";
        }

        return "redirect:/client";
    }

    @PostMapping("/client/update")
    public String updateClient(@RequestParam("clCode") String clCode,
                               @RequestParam("clName") String clName,
                               @RequestParam("clType") String clType,
                               @RequestParam("clPhone") String clPhone) {
        Clients client = new Clients();
        client.setClCode(clCode);
        client.setClName(clName);
        client.setClType(clType);
        client.setClPhone(clPhone);

        clientService.updateClient(client);
        return "redirect:/client";
    }
    @PostMapping(value = "/client/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportCodeToExcel(@RequestParam("clCode") List<String> clCode, HttpServletResponse response) throws IOException {
        List<Clients> clients = clientRepository.findByclCodeIn(clCode);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Codes");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"고객코드", "고객명", "고객분류","연락처","전체 수주량"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Clients clients1 : clients) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(clients1.getClCode());
            row.createCell(1).setCellValue(clients1.getClName());
            row.createCell(2).setCellValue(clients1.getClType());
            row.createCell(3).setCellValue(clients1.getClPhone());
            row.createCell(4).setCellValue(clients1.getClAmount());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ClientList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @GetMapping("/searchClient")
    public String getSearchClient(@RequestParam(required = false, name = "clCode") String clCode,
                                  @RequestParam(required = false, name = "clName") String clName,
                                  @RequestParam(required = false, name = "clType") String clType,
                                  Model model) {

        List<Clients> clientsList;

        // 고객 코드, 고객 명, 고객 분류가 모두 입력된 경우
        if (clCode != null && !clCode.isEmpty() && clName != null && !clName.isEmpty() && clType != null && !clType.isEmpty()) {
            clientsList = clientRepository.findByClCodeAndClNameAndClType(clCode, clName, clType);
        }
        // 고객 코드와 고객 명이 입력된 경우
        else if (clCode != null && !clCode.isEmpty() && clName != null && !clName.isEmpty()) {
            clientsList = clientRepository.findByClCodeAndClName(clCode, clName);
        }
        // 고객 코드와 고객 분류가 입력된 경우
        else if (clCode != null && !clCode.isEmpty() && clType != null && !clType.isEmpty()) {
            clientsList = clientRepository.findByClCodeAndClType(clCode, clType);
        }
        // 고객 명과 고객 분류가 입력된 경우
        else if (clName != null && !clName.isEmpty() && clType != null && !clType.isEmpty()) {
            clientsList = clientRepository.findByClNameAndClType(clName, clType);
        }
        // 고객 코드만 입력된 경우
        else if (clCode != null && !clCode.isEmpty()) {
            Clients client = clientRepository.findByClCode(clCode);
            if (client != null) {
                clientsList = Collections.singletonList(client);
            } else {
                clientsList = Collections.emptyList();
            }
        }
        // 고객 명만 입력된 경우
        else if (clName != null && !clName.isEmpty()) {
            clientsList = clientRepository.findByClName(clName);
        }
        // 고객 분류만 입력된 경우
        else if (clType != null && !clType.isEmpty()) {
            clientsList = clientRepository.findByClType(clType);
        }
        // 모든 입력값이 없는 경우
        else {
            clientsList = clientRepository.findAll();
        }

        model.addAttribute("clients", clientsList);

        // 기타 필요한 로직 추가 가능

        return "system/client"; // 적절한 뷰 이름으로 변경
    }



}
