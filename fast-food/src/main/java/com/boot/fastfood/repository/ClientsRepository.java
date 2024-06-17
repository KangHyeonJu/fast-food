package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, String> {
    Clients findByClName(String OO업체);
}