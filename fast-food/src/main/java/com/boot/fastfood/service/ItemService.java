package com.boot.fastfood.service;

import com.boot.fastfood.dto.AddItemDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Items save(AddItemDTO dto) {
        return itemRepository.save(dto.toEntity());
    }

    public List<Items> findAll() {
        return itemRepository.findAll();
    }
}
