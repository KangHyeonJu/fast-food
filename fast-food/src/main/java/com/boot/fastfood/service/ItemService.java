package com.boot.fastfood.service;

import com.boot.fastfood.dto.Item.AddItemDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.ItemRepository;
import com.boot.fastfood.repository.RoutingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RoutingRepository routingRepository;


    public Items findByitCode(String itCode) {
        return itemRepository.findById(itCode)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + itCode));
    };


    public List<Items> findByItType(String itType) {
        return itemRepository.findByItType(itType);
    }

    /*
    public List<Items> searchItem(String itCode, String itName, String itType) {
        return  itemRepository.findItems(itCode, itName, itType);
    }

     */

    public List<Items> search(String itCode, String itName, String itType) {
        List<Items> items = new ArrayList<>();
        try{
            if(itCode!=null & itName!=null &itType!=null
                    || !itCode.isEmpty() & !itName.isEmpty() & !itType.isEmpty()) {
                items = itemRepository.findItems(itCode, itName, itType);
            } else if(itCode!=null & itName!=null) {
                items = itemRepository.findByItCodeAndItName(itCode, itName);
            } else if(itCode!=null & itType!=null) {
                items = itemRepository.findByItCodeAndItType(itCode, itType);
            } else if(itName!=null & itType!=null) {
                items = itemRepository.findByItTypeAndItName(itType, itName);
            } else if(itCode!=null) {
                items = itemRepository.findByItCode(itCode);
            } else if(itName!=null) {
                items = itemRepository.findByItName(itName);
            } else if(itType!=null) {
                items = itemRepository.findByItType(itType);
            } else if(itCode==null & itName==null&itType==null) {
                items = itemRepository.findAll();
            }
            System.out.println(items);
            if(items.isEmpty()) {

            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("조회된 결과가 없습니다. ");
        }
        return items;
    }

    public Items save(AddItemDTO dto) {
        Items items = dto.toEntity();

        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        items.setItCode("IT" + nowTime);

        return itemRepository.save(items);
    }

    public List<Items> findAll() {
        return itemRepository.findAll();
    }

    public void deleteById(String itCode) {
        itemRepository.deleteById(itCode);
    }
}
