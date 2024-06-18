package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Item.AddItemDTO;
import com.boot.fastfood.dto.Item.ItemListDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;
    @PostMapping("/items")
    public ResponseEntity<Items> save(@RequestBody AddItemDTO dto) {
        Items items = itemService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(items);
    }


/*
    @GetMapping("/routing/{itCode}")
    public ResponseEntity<Items> item(@PathVariable String itCode) {
        Items item = itemService.findByitCode(itCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(item);
    }


 */



}