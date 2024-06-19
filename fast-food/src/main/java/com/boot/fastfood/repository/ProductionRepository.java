package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production, String> {
    Production findByContract(Contract contract);
}
