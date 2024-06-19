package com.boot.fastfood.service;

import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.WorksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorksService {
    private final WorksRepository worksRepository;

}
