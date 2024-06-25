package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.entity.Calendar;
import com.boot.fastfood.repository.CalendarRepository;
import com.boot.fastfood.service.CalendarService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final CalendarRepository calendarRepository;

    @GetMapping("/calendar")
    public String productPlan(Model model){
        List<Calendar> calendars = calendarRepository.findAll();
        model.addAttribute("calendars", calendars);
        return "ProductPages/calendar";
    }

    @PostMapping("/calendar/event")
    public ResponseEntity<?> saveEvent(@RequestBody Map<String, Object> eventData) {
        try {
            String title = (String) eventData.get("title");
            String startDateStr = (String) eventData.get("startDate");
            String endDateStr = (String) eventData.get("endDate");

            LocalDateTime startDate = LocalDateTime.parse(startDateStr, DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, DateTimeFormatter.ISO_DATE_TIME);

            Calendar calendar = new Calendar();
            calendar.setTitle(title);
            calendar.setSDate(LocalDate.from(startDate));
            calendar.setEDate(LocalDate.from(endDate));

            calendarService.saveEvent(calendar);
            return ResponseEntity.ok("Event saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save event: " + e.getMessage());
        }
    }
}
