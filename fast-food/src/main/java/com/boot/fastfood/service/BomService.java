package com.boot.fastfood.service;

import com.boot.fastfood.dto.Bom.AddBomDTO;
import com.boot.fastfood.dto.Materials.UpdateMaterialsDTO;
import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.repository.BomRepository;
import com.boot.fastfood.repository.ItemRepository;
import com.boot.fastfood.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BomService {

    private final BomRepository bomRepository;
    private final MaterialsRepository materialsRepository;
    private final ItemRepository itemRepository;

    public List<BOM> getItemByBom(Items item){
        return bomRepository.findByItems(item);
    }

    @Transactional
    public List<BOM> save(AddBomDTO dto) {

        List<BOM> dtoList = new ArrayList<>();
        int i = 0;
        for(String mtCode : dto.getMtCode()) {
            BOM bom = new BOM();
            Materials materials = materialsRepository.findById(mtCode)
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + mtCode));
            Items items = itemRepository.findById(dto.getItCode())
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + dto.getItCode()));
            bom.setItems(items);
            bom.setMaterials(materials);
            dtoList.add(bom);
        }
        return bomRepository.saveAll(dtoList);
    }

    @Transactional
    public BOM saveById(String itCode, String mtCode, UpdateMaterialsDTO mtAmount) {
        BOM bom = bomRepository.findByItCodeAndMtCode(itCode, mtCode);
        bom.update(mtAmount.getMtAmount());
        return bom;
    }

    public List<BOM> findByid(String itCode) {
        List<BOM> bomList = bomRepository.findByItems_ItCode(itCode);
        return bomList;
    }

    @Transactional
    public void delete(String itCode) {
        List<BOM> routingList = bomRepository.findByItems_ItCode(itCode);

        bomRepository.deleteAll(routingList);
    }
    @Transactional
    public void deleteById(String itCode, String mtCode) {
        BOM bom = bomRepository.findByItCodeAndMtCode(itCode, mtCode);

        bomRepository.delete(bom);
    }
}



