package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface CodesRepository extends JpaRepository<Codes, Long> {
    List<Codes> findBycState(boolean state);

    Codes findBycNo(Long cno);

    List<Codes> findBycNoIn(Collection<Long> cNo);

    List<Codes> findBycNameContaining(String cName);
}
