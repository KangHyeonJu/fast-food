package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Item.ItemListDTO;
import com.boot.fastfood.dto.Process.ProcessListDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.service.ItemService;
import com.boot.fastfood.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ProcessService processService;

    @GetMapping("/routing")
    public String itemList(Model model) {
        List<ItemListDTO> itemList = itemService.findAll()
                        .stream()
                                .map(ItemListDTO::new)
                                        .toList();

            List<ProcessListDTO> processList = processService.findAll()
                .stream()
                .map(ProcessListDTO::new)
                .toList();

        model.addAttribute("itemList", itemList);
        model.addAttribute("processList", processList);

        return "system/routing";
    }

    @GetMapping("/routing/{itCode}")
    public ItemListDTO item(@PathVariable String itCode) {
       Items item = itemService.findByitCode(itCode);

       return new ItemListDTO(item);
    }
}
