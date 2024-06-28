package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ClientRepository extends JpaRepository<Clients, String> {

    List<Clients> findByclCodeIn(Collection<String> clCode);

    Clients findByClCode(String clCode);

    List<Clients> findByClName(String clName);


    List<Clients> findByClCodeAndClName(String clCode, String clName);

    List<Clients> findByClCodeAndClNameAndClType(String clCode, String clName, String clType);

    List<Clients> findByClCodeAndClType(String clCode, String clType);

    List<Clients> findByClNameAndClType(String clName, String clType);

    List<Clients> findByClType(String clType);
}
