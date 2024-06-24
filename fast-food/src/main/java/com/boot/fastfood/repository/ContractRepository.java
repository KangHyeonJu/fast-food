package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByClientsClCode(String clCode);

    List<Contract> findByCtStatus(String ctStatus);

    List<Contract> searchContracts(ContractSearchDto searchDto);
}