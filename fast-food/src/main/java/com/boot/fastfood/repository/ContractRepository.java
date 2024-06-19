package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByClientsClCode(String clCode);

    List<Contract> findByCtStatus(String ctStatus);

}