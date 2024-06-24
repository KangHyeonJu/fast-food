package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.dto.ShipmentDto;
import com.boot.fastfood.entity.Employee;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Shipment;
import com.boot.fastfood.service.EmployeeService;
import com.boot.fastfood.service.ItemsService;
import com.boot.fastfood.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ItemsService itemsService;
    private final EmployeeService employeeService;

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
            shipmentDto.setSmSchedule(shipment.getContract().getDeliveryDate().minusDays(1));
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
}
