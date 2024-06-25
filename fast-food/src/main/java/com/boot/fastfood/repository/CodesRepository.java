package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface CodesRepository extends JpaRepository<Codes, Long> {
    List<Codes> findBycNoIn(Collection<Long> cNo);
}
