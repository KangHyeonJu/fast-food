package com.boot.fastfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;

@RestController
public class ClockController {

    private Clock clock;

    Clock systemClock = Clock.systemDefaultZone();
    Instant instant = Instant.now(systemClock); // 현재 시간을 Instant 객체로 얻음
    LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

    @Autowired
    public void AdminController(Clock clock) {
        this.clock = clock;
    }

    @PostMapping("/addDays")
    public void addDays(@RequestParam int days) {
        Instant currentInstant = clock.instant();
        ZonedDateTime newTime = currentInstant.atZone(ZoneId.systemDefault()).plusDays(days);
    }

    @GetMapping("/resetTime")
    public void resetTime() {
        this.clock = Clock.systemDefaultZone();
    }

    @Bean
    public Clock getClock() {
        return this.clock;
    }


}
