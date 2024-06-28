package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.dto.ProductionSearchDto;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Production;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, String> {
    List<Production> findByContract(Contract contract);
    
    Production findByPmCode(String pmCode);

    List<Production> findByPmCodeIn(List<String> pmCode);

}
