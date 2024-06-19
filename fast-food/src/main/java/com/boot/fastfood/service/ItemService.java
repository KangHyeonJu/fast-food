package com.boot.fastfood.service;

import com.boot.fastfood.dto.Item.AddItemDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public Items findByitCode(String itCode) {
        return itemRepository.findById(itCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + itCode));
    };

    public Items save(AddItemDTO dto) {
        Items items = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        items.setItCode("IT" + nowTime);

        return itemRepository.save(items);
    }

    public List<Items> findAll() {
        return itemRepository.findAll();
    }
}
