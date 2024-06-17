package com.boot.fastfood.service;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsService {
    private final ItemsRepository itemsRepository;
    public List<Items> getAllItems(){
        return itemsRepository.findAll();
    }
}
