package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ItemListDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/routing")
    public String itemList(Model model) {
        List<ItemListDTO> itemList = itemService.findAll()
                        .stream()
                                .map(ItemListDTO::new)
                                        .toList();

        model.addAttribute("itemList", itemList);

        return "system/routing";
    }
}
