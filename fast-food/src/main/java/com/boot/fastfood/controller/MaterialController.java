package com.boot.fastfood.controller;


import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import com.boot.fastfood.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MaterialController {

    private final WarehousingService warehousingService;
    private final WarehousingRepository warehousingRepository;

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    private final WorksService worksService;

    private final ReleasesService releasesService;
    private final ReleasesRepository releasesRepository;

    private final OrdersRepository ordersRepository;

    private final MaterialService materialService;
    private final MaterialRepository materialRepository;

    private final VendorService vendorService;


    @GetMapping("/warehousing")
    public String Warehousing(Model model) {

        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        List<Warehousing> warehousings = warehousingService.findAll();
        model.addAttribute("warehousings", warehousings);

        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);

        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        return "material/Warehousing";
    }

    @GetMapping("/searchWarehousing")
    public String searchWarehousing(
            @RequestParam(required = false, name = "odCode") String odCode,
            @RequestParam(required = false, name = "vdName") String vdName,
            @RequestParam(required = false, name = "whDate") LocalDate whDate,
            @RequestParam(required = false, name = "mtName") String mtName,
            @RequestParam(required = false, name = "emName") String emName,
            Model model) {

        List<Warehousing> warehousings = warehousingService.findAll();

        // 필터링 조건에 따라 검색 처리
        if (odCode != null && !odCode.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getOrders().getOdCode().contains(odCode))
                    .collect(Collectors.toList());
        }

        if (vdName != null && !vdName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getVendor().getVdName().contains(vdName))
                    .collect(Collectors.toList());
        }

        if (whDate != null) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getWhDate().equals(whDate))
                    .collect(Collectors.toList());
        }

        if (mtName != null && !mtName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getMaterials().getMtName().contains(mtName))
                    .collect(Collectors.toList());
        }

        if (emName != null && !emName.isEmpty()) {
            warehousings = warehousings.stream()
                    .filter(wh -> wh.getEmployee().getEmName().contains(emName))
                    .collect(Collectors.toList());
        }

        model.addAttribute("warehousings", warehousings);
        model.addAttribute("materials", materialService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        return "material/Warehousing";  // HTML 템플릿 파일 이름
    }

    @PostMapping(value = "/warehousing/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportWarehousingsToExcel(@RequestParam("whCode") List<String> whCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Warehousing> warehousings = warehousingRepository.findByWhCodeIn(whCode);

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Warehousing");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"발주 코드", "입고 업체", "입고일", "거래처", "원자재명", "입고량", "작업자"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Warehousing warehousing : warehousings) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(warehousing.getOrders().getOdCode());
            row.createCell(1).setCellValue(warehousing.getWhCode());

            // 입고일에 날짜 형식 적용
            Cell whDateCell = row.createCell(2);
            whDateCell.setCellValue(warehousing.getWhDate());
            whDateCell.setCellStyle(dateCellStyle);

            row.createCell(3).setCellValue(warehousing.getVendor().getVdName());
            row.createCell(4).setCellValue(warehousing.getMaterials().getMtName());
            row.createCell(5).setCellValue(warehousing.getOrders().getOdAmount());
            row.createCell(6).setCellValue(warehousing.getEmployee().getEmName());
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=WarehousingList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/material")
    public String getMaterialPage(Model model) {

        // 모든 입고 목록을 가져와서 모델에 추가
        List<Warehousing> warehousings = warehousingService.findAll();
        model.addAttribute("warehousings", warehousings);

        // 모든 자재 목록을 가져와서 모델에 추가
        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        // 모든 거래처 목록을 가져와서 모델에 추가
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        LocalDate today = LocalDate.now();
        List<Orders> futureOrders = ordersRepository.findByOdDueDateGreaterThanEqual(today);
        model.addAttribute("orders", futureOrders);


        List<Orders> todayOrders = ordersRepository.findByOdDueDate(LocalDate.now());
        model.addAttribute("todayOrders", todayOrders);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);


        return "material/Material";  // HTML 템플릿 파일 이름
    }

    @PostMapping("/material")
    public String updateWhStatus(@RequestParam("odCode") String odCode, @RequestParam("emName") String emName,
                                 @RequestParam("mtCode") String mtCode, @RequestParam("vdCode") String vdCode) {
        Orders order = ordersRepository.findByOdCode(odCode);
        Materials materials = materialRepository.findByMtCode(mtCode);
        if (order != null) {
            order.setWhStatus(1);
            ordersRepository.save(order);
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String whCode = "WH" + currentTime.format(formatter);

            Warehousing warehousing = new Warehousing();
            warehousing.setOrders(order);
            warehousing.setWhDate(LocalDate.now());
            warehousing.setWhCode(whCode);

            Employee employee = employeeRepository.findByEmName(emName);

            warehousing.setEmployee(employee);

            Optional<Vendor> VendorOptional = vendorService.findByVdCode(vdCode);

            if (VendorOptional.isPresent()) {
                warehousing.setVendor(VendorOptional.get());
            } else {
                throw new IllegalArgumentException(vdCode);
            }

            Optional<Materials> MaterialsOptional = materialService.findByMtCode(mtCode);

            if (MaterialsOptional.isPresent()) {
                warehousing.setMaterials(MaterialsOptional.get());
                warehousingRepository.save(warehousing);
            } else {
                throw new IllegalArgumentException(mtCode);
            }

            int addedStock = order.getOdAmount();
            int currentStock = materials.getMtStock();
            materials.setMtStock(currentStock + addedStock);
            materialRepository.save(materials);
        }
        return "redirect:/material";
    }

    @GetMapping("/searchMaterial")
    public String getSearchMaterial(@RequestParam(required = false, name = "odCode") String odCode,
                                    @RequestParam(required = false, name = "mtName") String mtName,
                                    @RequestParam(required = false, name = "odDueDate") LocalDate odDueDate,
                                    Model model) {

        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        List<Orders> orders;
        if (odCode != null && !odCode.isEmpty()) {
            Orders order = ordersRepository.findFirstByOdCode(odCode); // 단일 객체 반환 메서드 사용
            if (order != null && order.getWhStatus() == 0) {
                orders = Collections.singletonList(order);
            } else {
                orders = Collections.emptyList();
            }
        } else if (mtName != null && !mtName.isEmpty()) {
            orders = ordersRepository.findByMaterials_MtName(mtName)
                    .stream()
                    .filter(order -> order.getWhStatus() == 0)
                    .collect(Collectors.toList());
        } else if (odDueDate != null) {
            orders = ordersRepository.findByOdDueDate(odDueDate)
                    .stream()
                    .filter(order -> order.getWhStatus() == 0)
                    .collect(Collectors.toList());
        } else {
            orders = ordersRepository.findAll()
                    .stream()
                    .filter(order -> order.getWhStatus() == 0)
                    .collect(Collectors.toList());
        }

        model.addAttribute("orders", orders);

        // 금일 자재 입고 등록을 위한 정보 추가
        List<Orders> todayOrders = ordersRepository.findByOdDueDate(LocalDate.now());
        model.addAttribute("todayOrders", todayOrders);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "material/Material";
    }


    @GetMapping("/release")
    public String Release(Model model) {

        List<Materials> materials = materialService.findAll();
        model.addAttribute("materials", materials);

        List<Works> works = worksService.findAll();
        model.addAttribute("works", works);

        List<Releases> releases = releasesService.findAll();
        model.addAttribute("releases", releases);

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "material/Release";
    }

    @PostMapping("/release/save")
    @ResponseBody
    public ResponseEntity<String> saveRelease(@RequestParam("wkCode") String wkCode, @RequestParam("emCode") String emCode) {
        try {
            releasesService.saveRelease(wkCode, emCode);
            return ResponseEntity.ok("자재 출고가 성공적으로 등록되었습니다.");
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/searchRelease")
    public String searchRelease(
            @RequestParam(required = false, name = "rsDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rsDate,
            @RequestParam(required = false, name = "mtName") String mtName,
            @RequestParam(required = false, name = "wkCode") String wkCode,
            @RequestParam(required = false, name = "emName") String emName,
            Model model) {

        List<Releases> releases = releasesService.findAll();

        // 필터링 조건에 따라 검색 처리
        if (rsDate != null) {
            releases = releases.stream()
                    .filter(release -> release.getRsDate().equals(rsDate))
                    .collect(Collectors.toList());
        }

        if (mtName != null && !mtName.isEmpty()) {
            releases = releases.stream()
                    .filter(release -> release.getMaterials().getMtName().contains(mtName))
                    .collect(Collectors.toList());
        }

        if (wkCode != null && !wkCode.isEmpty()) {
            releases = releases.stream()
                    .filter(release -> release.getWorks().getWkCode().contains(wkCode))
                    .collect(Collectors.toList());
        }

        if (emName != null && !emName.isEmpty()) {
            releases = releases.stream()
                    .filter(release -> release.getEmployee().getEmName().contains(emName))
                    .collect(Collectors.toList());
        }

        model.addAttribute("releases", releases);
        model.addAttribute("materials", materialService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        return "material/Release";  // HTML 템플릿 파일 이름
    }

    @PostMapping(value = "/release/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportReleaseToExcel(@RequestParam("rsCode") List<String> rsCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Releases> releases = releasesRepository.findByRsCodeIn(rsCode);

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Releases");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"작업 지시 코드", "출고 코드", "출고일", "출고량", "원자재명", "작업자"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Releases release : releases) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(release.getWorks().getWkCode());
            row.createCell(1).setCellValue(release.getRsCode());

            // 출고일에 날짜 형식 적용
            Cell rsDateCell = row.createCell(2);
            rsDateCell.setCellValue(release.getRsDate());
            rsDateCell.setCellStyle(dateCellStyle);

            row.createCell(3).setCellValue(release.getRsAmount());
            row.createCell(4).setCellValue(release.getMaterials().getMtName());
            row.createCell(5).setCellValue(release.getEmployee().getEmName());
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ReleaseList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
