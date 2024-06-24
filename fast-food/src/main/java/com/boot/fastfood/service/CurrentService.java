package com.boot.fastfood.service;

import com.boot.fastfood.dto.MonthChartDto;
import com.boot.fastfood.dto.YearChartDto;
import com.boot.fastfood.repository.CurrentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentService {
    private final CurrentRepository currentRepository;

    public Map<Integer, List<MonthChartDto>> getMonthCart(){
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(6);

        List<MonthChartDto> monthChartDtoList =  currentRepository.findMonthlyContractsByYear(startDate, endDate);
        return monthChartDtoList.stream().collect(Collectors.groupingBy(MonthChartDto::getMonth));
    }

    public Map<Integer, List<YearChartDto>> getYearCart() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(4);

        List<YearChartDto> yearChartDtoList =  currentRepository.findYearlyContracts(startDate, endDate);
        return yearChartDtoList.stream().collect(Collectors.groupingBy(YearChartDto::getYear));
    }
}
