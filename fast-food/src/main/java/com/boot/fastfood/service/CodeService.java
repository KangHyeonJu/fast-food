package com.boot.fastfood.service;

import com.boot.fastfood.entity.Codes;
import com.boot.fastfood.repository.CodesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CodeService {
    private final CodesRepository codesRepository;

    public Codes saveCode(Codes codes){
        return codesRepository.save(codes);
    }

    public List<Codes> getAllCodes(){
        return codesRepository.findBycState(false);
    }

    public Codes findByCNo(Long cno){
        return codesRepository.findBycNo(cno);
    }


}
