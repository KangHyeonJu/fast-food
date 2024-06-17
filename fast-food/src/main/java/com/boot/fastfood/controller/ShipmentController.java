package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ShipSearchDto;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Shipment;
import com.boot.fastfood.service.ItemsService;
import com.boot.fastfood.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ItemsService itemsService;

    @GetMapping("/shipment")
    public String shipmentPage(ShipSearchDto shipSearchDto, Model model){
        List<Shipment> shipments = shipmentService.getShipmentList(shipSearchDto);
        List<Items> items = itemsService.getAllItems();

        model.addAttribute("shipments", shipments);
        model.addAttribute("shipSearchDto", shipSearchDto);
        model.addAttribute("items", items);

        return "shipment/shipment";
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
