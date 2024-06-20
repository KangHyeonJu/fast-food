package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.dto.OrderTodayDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.MaterialRepository;
import com.boot.fastfood.repository.ProductionRepository;
import com.boot.fastfood.service.BomService;
import com.boot.fastfood.service.ContractService;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.MaterialService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MaterialOrderController {
    private final ContractService contractService;
    private final BomService bomService;
    private final ProductionRepository productionRepository;
    private final MaterialRepository materialRepository;
    private final EmployeeService employeeService;
    private final MaterialService materialService;

    @GetMapping("/order_plan")
    public String order_plan(Model model) {
        List<Contract> contracts = contractService.getAllContract();
        List<MaterialOrderDto> materialOrderDtoList = new ArrayList<>();
        List<Materials> materialsList = materialRepository.findAll();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Contract contract : contracts) {
            MaterialOrderDto materialOrderDto = new MaterialOrderDto();
            materialOrderDto.setContract(contract);
            materialOrderDto.setItems(contract.getItems());

            Production production = productionRepository.findByContract(contract);
            materialOrderDto.setProduction(production);
            materialOrderDto.setAmount(production.getPmAmount() * contract.getItems().getItEa());

            List<BOM> bomList = bomService.getItemByBom(contract.getItems());
            String[][] bomAmount = new String[bomList.size()][2];

            for (int i = 0; i < bomList.size(); i++) {
                bomAmount[i][0] = bomList.get(i).getMaterials().getMtName();
                bomAmount[i][1] = Integer.toString((int) Math.ceil(bomList.get(i).getMtAmount() * production.getPmAmount() * contract.getItems().getItEa()));
            }

            materialOrderDto.setBomList(bomList);
            materialOrderDto.setBomListAmount(bomAmount);

            materialOrderDtoList.add(materialOrderDto);
        }

        List<OrderTodayDto> orderTodayDtoList = new ArrayList<>();

        for (Materials materials : materialsList){
            OrderTodayDto orderTodayDto = new OrderTodayDto();
            orderTodayDto.setMaterials(materials);
            orderTodayDtoList.add(orderTodayDto);
        }

        for (MaterialOrderDto materialOrderDto: materialOrderDtoList){
            if(materialOrderDto.getProduction().getPmSDate().minusDays(2).equals(LocalDate.now())){
                String[][] todayOrder = materialOrderDto.getBomListAmount();

                for (OrderTodayDto orderTodayDto : orderTodayDtoList){
                    orderTodayDto.setContract(materialOrderDto.getContract());
                    int amount = orderTodayDto.getOrderAmount();
                    for(int i=0; i<todayOrder.length; i++) {
                        if (Objects.equals(todayOrder[i][0], orderTodayDto.getMaterials().getMtName())) {
                            amount += Integer.parseInt(todayOrder[i][1]);
                            orderTodayDto.setOrderAmount(amount);
                        }

                    }
                }

            }
        }

        JSONArray jsonArray = new JSONArray(orderTodayDtoList);
        JSONObject finalJsonObject = new JSONObject();

        finalJsonObject.put("orderJson", jsonArray);

        System.out.println(finalJsonObject);


        model.addAttribute("contracts", materialOrderDtoList);
        model.addAttribute("materialsList", materialsList);
        model.addAttribute("orderTodayDtoList", orderTodayDtoList);
        model.addAttribute("employees", employees);
//        model.addAttribute("jsonObject", jsonObject);


        return "material/Order_plan";
    }




    //발주등록
    @PostMapping("/order_plan/add/{emName}")
    public String orderAdd(@PathVariable String emName, @RequestBody List<OrderTodayDto> orderTodayDtoList){
        try {
            System.out.println("들어와?");
            materialService.orderAdd(emName, orderTodayDtoList);
            return "redirect:/order_plan";
        }catch (Exception e){
            return "redirect:/order_plan";
        }
    }
}
