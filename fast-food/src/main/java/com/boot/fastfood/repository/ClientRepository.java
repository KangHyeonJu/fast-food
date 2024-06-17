package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Clients, String> {

}
