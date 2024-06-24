package com.boot.fastfood.service;

import com.boot.fastfood.dto.MonthChartDto;
import com.boot.fastfood.dto.YearChartDto;
import com.boot.fastfood.repository.CurrentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentService {
    private final CurrentRepository currentRepository;

    public Map<Integer, List<MonthChartDto>> getMonthCart(int year){
        List<MonthChartDto> monthChartDtoList =  currentRepository.findMonthlyContractsByYear(year);
        return monthChartDtoList.stream().collect(Collectors.groupingBy(MonthChartDto::getMonth));
    }

    public Map<Integer, List<YearChartDto>> getYearCart() {
        List<YearChartDto> yearChartDtoList =  currentRepository.findYearlyContracts();
        return yearChartDtoList.stream().collect(Collectors.groupingBy(YearChartDto::getYear));
    }
}
