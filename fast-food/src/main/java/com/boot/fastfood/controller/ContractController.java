package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ContractRepository;
import com.boot.fastfood.service.ClientService;
import com.boot.fastfood.service.ContractService;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/contract")
    public String contract(Model model) {
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts", contracts);

        // 다른 필요한 데이터들도 함께 모델에 추가
        List<Clients> clients = clientService.getAllClients();
        List<Items> items = itemsService.findAll();
        List<Employee> employees = employeeService.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("items", items);
        model.addAttribute("employees", employees);

        return "contract/Contract"; // 초기에 모든 수주가 보이는 페이지로 이동
    }

    @GetMapping("/searchcontract")
    public String searchContracts(@RequestParam(name = "ctCode", required = false) String ctCode,
                                  @RequestParam(name = "clName", required = false) String clName,
                                  @RequestParam(name = "ctDate", required = false) LocalDate ctDate,
                                  @RequestParam(name = "itName", required = false) String itName,
                                  @RequestParam(name = "emName", required = false) String emName,
                                  Model model) {

        // 검색 조건을 객체에 설정
        ContractSearchDto searchDto = new ContractSearchDto(ctCode, clName, ctDate, itName, emName);

        // 검색 결과 조회
        List<Contract> searchcontracts = contractService.searchContracts(searchDto);

        // 검색 결과와 검색 조건을 모델에 추가
        model.addAttribute("contracts", searchcontracts);
        model.addAttribute("searchDto", searchDto);

        // 다른 필요한 데이터들도 함께 모델에 추가
        List<Clients> clients = clientService.getAllClients();
        List<Items> items = itemsService.findAll();
        List<Employee> employees = employeeService.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("items", items);
        model.addAttribute("employees", employees);

        return "contract/Contract"; // 검색 결과가 보이는 페이지로 이동
    }

    @PostMapping("/contract/add")
    public String addContract(@ModelAttribute ContractDto contractDto, Model model) {
        try {

            contractService.saveContract(contractDto); // 수주 저장
            model.addAttribute("message", "수주가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "수주 등록 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/contract"; // 수주 관리 페이지로 리다이렉트
    }

    @PostMapping(value = "/contract/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportContractsToExcel(@RequestParam("ctCode") List<String> ctCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Contract> contracts = contractRepository.findByCtCodeIn(ctCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contract");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"수주 코드", "고객 업체", "제품명", "수주 수량", "수주 날짜", "납품일", "납품 장소", "작업자", "수주 상태"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Contract contract : contracts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contract.getCtCode());
            row.createCell(1).setCellValue(contract.getClients().getClName());
            row.createCell(2).setCellValue(contract.getItems().getItName());
            row.createCell(3).setCellValue(contract.getCtAmount());

            // 수주 날짜에 날짜 형식 적용
            Cell ctDateCell = row.createCell(4);
            ctDateCell.setCellValue(contract.getCtDate());
            ctDateCell.setCellStyle(dateCellStyle);

            // 납품일에 날짜 형식 적용
            Cell deliveryDateCell = row.createCell(5);
            deliveryDateCell.setCellValue(contract.getDeliveryDate());
            deliveryDateCell.setCellStyle(dateCellStyle);

            row.createCell(6).setCellValue(contract.getDeliveryPlace());
            row.createCell(7).setCellValue(contract.getEmployee().getEmName());
            row.createCell(8).setCellValue(contract.getCtStatus());

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ContractList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}