package com.boot.fastfood.controller;

import com.boot.fastfood.dto.MaterialOrderDto;
import com.boot.fastfood.dto.OrderSearchDto;
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

        Map<String, Object> orderPlanData = materialService.getOrderPlan();
        model.addAttribute("contracts", orderPlanData.get("materialOrderDtoList"));
        model.addAttribute("materialsList", orderPlanData.get("materialsList"));
        model.addAttribute("orderTodayDtoList", orderPlanData.get("orderTodayDtoList"));

        model.addAttribute("employees", employees);

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
            Map<String, Object> orderPlanData = materialService.getOrderPlan();
//            List<OrderTodayDto> orderTodayDtoList = (List<OrderTodayDto>) orderPlanData.get("orderTodayDtoList");

            Object orderTodayDtoListObject = orderPlanData.get("orderTodayDtoList");
            List<OrderTodayDto> orderTodayDtoList = new ArrayList<>();
            if (orderTodayDtoListObject instanceof List<?>) {
                for (Object obj : (List<?>) orderTodayDtoListObject) {
                    if (obj instanceof OrderTodayDto) {
                        orderTodayDtoList.add((OrderTodayDto) obj);
                    }
                }
            }

            materialService.orderAdd(emName, orderTodayDtoList);
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
    public String orderBox(@RequestBody Map<String, Object> paramMap, RedirectAttributes redirectAttributes){
        String emName = (String)paramMap.get("emName");
        String boxNum = (String)paramMap.get("boxNum");

        Materials box = materialRepository.findByMtName("Box");
        List<Orders> orders = ordersRepository.findByOdDateAndMaterials(LocalDate.now(), box);

        if (orders.isEmpty()){
            materialService.orderBox(emName, Integer.parseInt(boxNum));
            redirectAttributes.addFlashAttribute("message", "발주등록되었습니다.");
        }else {
            redirectAttributes.addFlashAttribute("message", "오늘 발주내역이 있습니다.");
        }
        return "redirect:/order_plan";
    }

    //포장지 발주
    @PostMapping("/order_plan/wrap")
    public String orderWrap(@RequestBody Map<String, Object> paramMap){
        String emName = (String)paramMap.get("emName");
        String wrapNum = (String)paramMap.get("wrapNum");

        materialService.orderWrap(emName, Integer.parseInt(wrapNum));

        return "redirect:/order_plan";
    }

}
