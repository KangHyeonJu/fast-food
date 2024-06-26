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
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String orderAdd(@PathVariable String emName){
        try {
            List<Orders> orderToday = ordersRepository.findByOdDateAndOdState(LocalDate.now(), false);

            materialService.orderAdd(emName, orderToday);
            return "redirect:/order_plan";
        }catch (Exception e){
            return "redirect:/order_plan";
        }
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

}
