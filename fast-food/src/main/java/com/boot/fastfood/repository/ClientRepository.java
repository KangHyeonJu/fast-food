package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ClientRepository extends JpaRepository<Clients, String> {

    List<Clients> findByclCodeIn(Collection<String> clCode);
}
