package com.boot.fastfood.service;

import com.boot.fastfood.dto.MonthChartDto;
import com.boot.fastfood.dto.YearChartDto;
import com.boot.fastfood.repository.CurrentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrentService {
    private final CurrentRepository currentRepository;

//    public List<MonthChartDto> getMonthCart(int year){
//        return currentRepository.findMonthlyContractsByYear(year);
//    }
//
//    public List<YearChartDto> getYearCart() {
//        return currentRepository.findYearlyContracts();
//    }
}
