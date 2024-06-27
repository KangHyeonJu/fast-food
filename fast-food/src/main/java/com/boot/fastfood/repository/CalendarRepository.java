package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, String> {

}
