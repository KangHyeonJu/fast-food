package com.boot.fastfood.service;

import com.boot.fastfood.entity.Works;
import com.boot.fastfood.repository.WorksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorksService {
    private final WorksRepository worksRepository;

    public List<Works> findAll() {
        return worksRepository.findAll();
    }

    public void saveRSDate(Works works){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        works.setRSDate(time);
        worksRepository.save(works);
    }

    public void saveREDate(Works works){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        works.setREDate(time);
        worksRepository.save(works);
    }

}
