package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Process.ProcessDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.dto.Routing.DeleteRoutingDTO;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.repository.ItemRepository;
import com.boot.fastfood.repository.RoutingRepository;
import com.boot.fastfood.service.ItemService;
import com.boot.fastfood.service.ProcessService;
import com.boot.fastfood.service.RoutingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoutingApiController {
    private final ProcessService processService;
    private final RoutingService routingService;
    private final ItemService itemService;
    private final RoutingRepository routingRepository;
    private final ItemRepository itemRepository;

    @PostMapping("/routing")
    public ResponseEntity<?> saveRouting(@RequestBody AddRoutingDTO dto) {

        List<Routing> routingList = routingService.findByid(dto.getItCode());
        List<Routing> routing;

        if(routingList.isEmpty()) {
            routing = routingService.save(dto);
        } else {
            routingService.delete(dto.getItCode());
            routing = routingService.save(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routing);
    }

    @GetMapping("/routing/{itCode}")
    public ResponseEntity<?> item(@PathVariable("itCode") String itCode) {

        List<Routing> routingList = routingService.findByid(itCode);
        ProcessListDTO listDTO = new ProcessListDTO();
        for(Routing routing : routingList) {
            String pcCode = routing.getProcess().getPcCode();
            Process process = processService.findById(pcCode);
            ProcessDTO dto = new ProcessDTO();
            dto.setPcCode(process.getPcCode());
            dto.setPcName(process.getPcName());
            dto.setPcCnt(process.getPcCnt());
            dto.setFacility(process.getFacilities());
            listDTO.addProcess(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(listDTO);
    }

    @DeleteMapping("/routing/{itCode}/{pcCode}")
    public ResponseEntity<?> deleteRouting(@PathVariable("itCode") String itCode, @PathVariable("pcCode") String pcCode) {
        routingService.deleteById(itCode, pcCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/routing/export/excel", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void exportContractsToExcel(@RequestParam("itCode") List<String> itCode, HttpServletResponse response) throws IOException {
        // 데이터베이스에서 엑셀 데이터 생성


        List<Items> itemsList = itemRepository.findByItCodeIn(itCode);

        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Routing");

        // 엑셀 헤더 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"품목 코드", "품목명", "품목분류", "개수"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 추가
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd")); // 날짜 형식 지정

        for (Items items : itemsList) {

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(items.getItCode());
            row.createCell(1).setCellValue(items.getItName());
            row.createCell(2).setCellValue(items.getItType());
            row.createCell(3).setCellValue(items.getItEa());
        }

        // 각 열의 너비를 자동으로 조정
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 엑셀 파일 응답으로 전송
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=RoutingList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
