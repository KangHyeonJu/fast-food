package com.boot.fastfood.service;

import com.boot.fastfood.dto.WarehousingDto;
import com.boot.fastfood.entity.*;
import com.boot.fastfood.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WarehousingService {

    private final WarehousingRepository warehousingRepository;

    // 모든 입고 목록을 가져오는 메서드
    public List<Warehousing> findAll() {

        return warehousingRepository.findAll();
    }
}
