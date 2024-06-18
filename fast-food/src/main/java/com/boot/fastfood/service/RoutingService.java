package com.boot.fastfood.service;

import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Process;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.repository.ItemRepository;
import com.boot.fastfood.repository.ProcessRepository;
import com.boot.fastfood.repository.RoutingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.boot.fastfood.entity.QRoutingId.routingId;
import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
@RequiredArgsConstructor
public class RoutingService {

    private final RoutingRepository routingRepository;
    private final ItemRepository itemRepository;
    private final ProcessRepository processRepository;

    public Routing save(AddRoutingDTO dto) {

        Items item = itemRepository.findById(dto.getItCode())
                .orElseThrow(() -> new IllegalArgumentException("not found : " + dto.getItCode()));

        Process process = processRepository.findById(dto.getPcCode())
                        .orElseThrow(() -> new IllegalArgumentException("not found : " + dto.getPcCode()));

        Routing routing = Routing.builder()
                .items(item)
                .process(process)
                .sequence(dto.getSequence())
                        .build();

        return routingRepository.save(routing);
    }

    public List<Routing> findByid(String itCode) {
        List<Routing> routing = routingRepository.findByItCode(itCode);

        return routing;
               // .orElseThrow(() -> new IllegalArgumentException("not found : " + itCode));
    }




}
