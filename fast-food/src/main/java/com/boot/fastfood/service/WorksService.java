package com.boot.fastfood.service;

import com.boot.fastfood.entity.Production;
import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.WorksRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorksService {
    private final WorksRepository worksRepository;

    public List<Works> findAll() {
        return worksRepository.findAll();
    }


    public List<Works> findByProductionPmCode(String pmCode) {

        System.out.println("22222222222222" + pmCode);

        return worksRepository.findByProductionPmCode(pmCode);
    }

}
