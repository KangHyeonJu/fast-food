package com.boot.fastfood.controller;

import com.boot.fastfood.dto.Routing.AddRoutingDTO;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Routing;
import com.boot.fastfood.service.ItemService;
import com.boot.fastfood.service.ProcessService;
import com.boot.fastfood.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoutingApiController {
    private final ProcessService processService;
    private final RoutingService routingService;
    private final ItemService itemService;

    @PostMapping("/routing")
    public ResponseEntity<Routing> saveRouting(@RequestBody AddRoutingDTO dto) {

        Routing routing = routingService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routing);
    }


    @GetMapping("/routing/{itCode}")
    public ResponseEntity<?> item(@PathVariable String itCode) {
        List<Routing> routing = routingService.findByid(itCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(routing);
    }


}
