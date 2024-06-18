package com.boot.fastfood.service;

import com.boot.fastfood.dto.ProductionDto;
import com.boot.fastfood.entity.Production;
import com.boot.fastfood.repository.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        dto.setCtCode(production.getContract().getCtCode());
        dto.setPmCode(production.getPmCode());
        dto.setPmSDate(production.getPmSDate());
        dto.setPmEDate(production.getPmEDate());
        dto.setCtAmount(production.getCtAmount());
        dto.setItCode(production.getItCode().getItCode());
        dto.setItName(production.getItCode().getItName());
        return dto;
    }
}
