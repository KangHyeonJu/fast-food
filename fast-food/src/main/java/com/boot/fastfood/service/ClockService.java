package com.boot.fastfood.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClockService {
    private final Clock clock;

    public LocalDate getCurrentDate() {
        return LocalDate.now(clock);
    }

}
