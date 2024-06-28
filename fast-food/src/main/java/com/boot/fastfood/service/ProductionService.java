package com.boot.fastfood.service;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.dto.ProductionSearchDto;
import com.boot.fastfood.entity.Production;
import com.boot.fastfood.repository.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductionService {
    private final ProductionRepository productionRepository;

    //생산 계획 보이기
    public List<ProductionDto> getAllProductions(){
        List<Production> productions = productionRepository.findAll();
        return productions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //생산 계획에 완제품코드로 완제품 이름 가져오기
    private ProductionDto convertToDto(Production production) {
        ProductionDto dto = new ProductionDto();
        dto.setCtCode(production.getContract().getCtCode());
        dto.setPmCode(production.getPmCode());
        dto.setPmSDate(production.getPmSDate());
        dto.setPmEDate(production.getPmEDate());
        dto.setPmAmount(production.getPmAmount());
//        dto.setItCode(production.getItName().getItCode());
        dto.setItName(production.getItName().getItName());
        return dto;
    }

    public List<Production> findAll() {
        return productionRepository.findAll();
    }

    public List<Production> findAllProductionsWithItems() {
        List<Production> productions = productionRepository.findAll();
        productions.forEach(production -> Hibernate.initialize(production.getItName()));
        return productions;
    }

    public List<ProductionDto> findByPmCodeIn(List<String> pmCode) {
        List<Production> productions = productionRepository.findByPmCodeIn(pmCode);
        return productions.stream().map(production -> {
            ProductionDto dto = new ProductionDto();
            dto.setPmCode(production.getPmCode());
            dto.setPmSDate(production.getPmSDate());
            dto.setPmEDate(production.getPmEDate());
            dto.setItName(production.getItName().getItName());
            dto.setPmAmount(production.getPmAmount());
            return dto;
        }).collect(Collectors.toList());
    }

}
