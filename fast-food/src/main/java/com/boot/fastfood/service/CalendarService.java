package com.boot.fastfood.service;

import com.boot.fastfood.entity.Calendar;
import com.boot.fastfood.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void saveEvent(Calendar calendar) {
        calendarRepository.save(calendar);
    }
    public List<Calendar> getAllEvents() {
        return calendarRepository.findAll();
    }
}
