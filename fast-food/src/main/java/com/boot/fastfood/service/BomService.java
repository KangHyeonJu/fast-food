package com.boot.fastfood.service;

import com.boot.fastfood.entity.BOM;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.repository.BomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BomService {
    private final BomRepository bomRepository;

    public List<BOM> getItemByBom(Items item){
        return bomRepository.findByItems(item);
    }
}
