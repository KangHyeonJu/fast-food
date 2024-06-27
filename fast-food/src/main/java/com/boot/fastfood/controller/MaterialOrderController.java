package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.dto.OrderSearchDto;
import com.boot.fastfood.dto.OrderSummaryDto;
import com.boot.fastfood.dto.OrderTodayDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.MaterialRepository;
import com.boot.fastfood.repository.OrdersRepository;
import com.boot.fastfood.repository.ProductionRepository;
import com.boot.fastfood.service.*;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MaterialOrderController {
    private final EmployeeService employeeService;
    private final MaterialService materialService;
    private final OrdersService ordersService;
    private final MaterialRepository materialRepository;
    private final OrdersRepository ordersRepository;

    //발주 예정 조회
    @GetMapping("/order_plan")
    public String order_plan(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();

        Map<LocalDate, List<OrderSummaryDto>> ordersByDate = ordersService.getOrderPlanGroupedByDate();
        List<OrderSummaryDto> orderToday = ordersService.getOrderSumToday();

        model.addAttribute("employees", employees);
        model.addAttribute("ordersByDate", ordersByDate);
        model.addAttribute("orderTodayList", orderToday);

        Materials box = materialRepository.findByMtName("Box");
        Materials wrap = materialRepository.findByMtName("포장지");

        model.addAttribute("box", box);
        model.addAttribute("wrap", wrap);

        return "material/Order_plan";
    }


    //발주 등록
    @PostMapping("/order_plan/add/{emName}")
    @ResponseBody
    public Map<String, String> orderAdd(@PathVariable String emName){
        List<Orders> orderToday = ordersRepository.findByOdDateAndOdState(LocalDate.now(), false);

        List<Orders> orders = ordersService.getOrdersByStateAndDate(true, LocalDate.now());

        Map<String, String> response = new HashMap<>();

        if (orders.isEmpty()){
            materialService.orderAdd(emName, orderToday);
            response.put("message", "발주등록되었습니다.");
        }else {
            response.put("message", "오늘 발주내역이 있습니다.");
        }
        return response;
    }

    //발주 내역 조회
    @GetMapping("/order")
    public String orderList(OrderSearchDto orderSearchDto, Model model){
        List<Orders> orders = ordersService.getOrderList(orderSearchDto);
        List<Employee> employees = employeeService.getAllEmployees();
        List<Materials> materialsList = materialRepository.findAll();

        model.addAttribute("orders", orders);
        model.addAttribute("employees", employees);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("orderSearchDto", orderSearchDto);

        return "material/Order";
    }

    //박스 발주
    @PostMapping("/order_plan/box")
    @ResponseBody
    public Map<String, String> orderBox(@RequestBody Map<String, Object> paramMap){
        String emName = (String)paramMap.get("emName");
        String boxNum = (String)paramMap.get("boxNum");

        Materials box = materialRepository.findByMtName("Box");
        Orders orders = ordersRepository.findByOdDateAndMaterials(LocalDate.now(), box);

        Map<String, String> response = new HashMap<>();
        if (orders == null){
            materialService.orderBox(emName, Integer.parseInt(boxNum));
            response.put("message", "발주등록되었습니다.");
        }else {
            response.put("message", "오늘 발주내역이 있습니다.");
        }
        return response;
    }

    //포장지 발주
    @PostMapping("/order_plan/wrap")
    @ResponseBody
    public Map<String, String> orderWrap(@RequestBody Map<String, Object> paramMap){
        String emName = (String)paramMap.get("emName");
        String wrapNum = (String)paramMap.get("wrapNum");

        Materials wrap = materialRepository.findByMtName("포장지");
        Orders orders = ordersRepository.findByOdDateAndMaterials(LocalDate.now(), wrap);

        Map<String, String> response = new HashMap<>();
        if (orders == null){
            materialService.orderWrap(emName, Integer.parseInt(wrapNum));
            response.put("message", "발주등록되었습니다.");
        }else {
            response.put("message", "오늘 발주내역이 있습니다.");
        }
        return response;
    }


    @PostMapping(value = "/order/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportContractsToExcel(@RequestParam("odCode") List<String> odCode, HttpServletResponse response) throws IOException {

        // 데이터베이스에서 엑셀 데이터 생성
        List<Orders> orders = ordersRepository.findByOdCodeIn(odCode);

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contract");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"발주 코드", "수주 코드", "발주일", "원자재업체명", "원자재명", "발주량", "작업자"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            // 헤더 셀 너비 자동 조정
            sheet.autoSizeColumn(i);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Orders order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getOdCode());

            if (order.getProduction().getContract() != null && order.getProduction().getContract().getCtCode() != null) {
                row.createCell(1).setCellValue(order.getProduction().getContract().getCtCode());
            } else {
                row.createCell(1).setCellValue("");
            }

            // 발주일에 날짜 형식 적용
            Cell dateCell = row.createCell(2);
            dateCell.setCellValue(order.getOdDate());
            dateCell.setCellStyle(dateCellStyle);

            row.createCell(3).setCellValue(order.getMaterials().getVendor().getVdName());
            row.createCell(4).setCellValue(order.getMaterials().getMtName());
            row.createCell(5).setCellValue(order.getOdAmount());
            row.createCell(6).setCellValue(order.getEmployee().getEmName());

            // 데이터 셀 너비 자동 조정
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=OrderList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }



}
