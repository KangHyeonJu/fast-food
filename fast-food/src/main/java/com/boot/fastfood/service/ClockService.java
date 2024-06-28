package com.boot.fastfood.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
public class ClockService {
    private Clock clock;

    @Autowired
    public void OrderService(Clock clock) {
        this.clock = clock;
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now(clock);
    }

}
