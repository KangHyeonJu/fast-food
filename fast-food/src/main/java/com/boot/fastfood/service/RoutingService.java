package com.boot.fastfood.service;

import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.dto.Routing.DeleteRoutingDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.repository.ItemRepository;
import com.boot.fastfood.repository.ProcessRepository;
import com.boot.fastfood.repository.RoutingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingService {

    private final RoutingRepository routingRepository;
    private final ItemRepository itemRepository;
    private final ProcessRepository processRepository;

    @Transactional
    public List<Routing> save(AddRoutingDTO dto) {

        List<Routing> dtoList = new ArrayList<>();
        int i = 0;
        for(String pcCode : dto.getPcCode()) {
            i++;
            Routing routing = new Routing();
            Process process = processRepository.findById(pcCode)
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + pcCode));
            Items items = itemRepository.findById(dto.getItCode())
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + dto.getItCode()));
            routing.setItems(items);
            routing.setProcess(process);
            routing.setSequence(i);
            dtoList.add(routing);

        }
        return routingRepository.saveAll(dtoList);
    }

    public List<Routing> findByid(String itCode) {
        List<Routing> routingList = routingRepository.findByItems_ItCode(itCode);
        return routingList;
    }

    public List<Routing> findByPcCode(String itCode) {
        return routingRepository.findByItems_ItCode(itCode);
    }

    @Transactional
    public void delete(String itCode) {
        List<Routing> routingList = routingRepository.findByItems_ItCode(itCode);

        routingRepository.deleteAll(routingList);
    }

    @Transactional
    public void deleteById(String itCode, String pcCode) {
        Routing routing = routingRepository.findByItCodeAndPcCode(itCode, pcCode);

        routingRepository.delete(routing);
    }

    public List<Routing> findAll() {
        return routingRepository.findAll();
    }

}
