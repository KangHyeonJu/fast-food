package com.boot.fastfood.repository;

import com.boot.fastfood.dto.MonthChartDto;
import com.boot.fastfood.dto.YearChartDto;
import com.boot.fastfood.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrentRepository extends JpaRepository<Contract, String> {

    @Query("SELECT new com.boot.fastfood.dto.MonthChartDto(c.items.itName, MONTH(c.ctDate), SUM(c.ctAmount)) " +
            "FROM Contract c WHERE YEAR(c.ctDate) = :year GROUP BY c.items.itName, MONTH(c.ctDate)")
    List<MonthChartDto> findMonthlyContractsByYear(@Param("year") int year);

    @Query("SELECT new com.boot.fastfood.dto.YearChartDto(c.items.itName, YEAR(c.ctDate), SUM(c.ctAmount)) " +
            "FROM Contract c GROUP BY c.items.itName, YEAR(c.ctDate)")
    List<YearChartDto> findYearlyContracts();
}
