package com.boot.fastfood.controller;

import com.boot.fastfood.dto.AddItemDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
