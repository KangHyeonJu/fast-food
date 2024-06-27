package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Warehousing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WarehousingRepository extends JpaRepository<Warehousing, Long> {

    List<Warehousing> findByWhCodeIn(List<String> whCode);
}
