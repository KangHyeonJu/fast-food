package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Item.AddItemDTO;
import com.boot.fastfood.dto.Item.ItemListDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/items/{itemCheck}")
    public ResponseEntity<?> deleteById(@PathVariable String itemCheck) {
        itemService.deleteById(itemCheck);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/itemSearch")
    public ResponseEntity<?> itemSearch(@RequestParam(required = false, name = "itCode") String itCode,
                                        @RequestParam(required = false, name="itName") String itName,
                                        @RequestParam(required = false, name="itType") String itType) {
        List<Items> items = itemService.search(itCode, itName, itType);

        return ResponseEntity.status(HttpStatus.OK)
                .body(items);
    }





}
