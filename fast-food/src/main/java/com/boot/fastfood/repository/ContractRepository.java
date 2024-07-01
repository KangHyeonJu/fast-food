package com.boot.fastfood.repository;

import com.boot.fastfood.dto.ContractDto;
import com.boot.fastfood.dto.ContractSearchDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.entity.Contract;
import com.boot.fastfood.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByClientsClCode(String clCode);

    List<Contract> findByClients(Clients clients);

    List<Contract> findByCtStatus(String ctStatus);

    List<Contract> searchContracts(ContractSearchDto searchDto);

    List<Contract> findByCtCodeIn(List<String> ctCodes);


    @Query("SELECT c FROM Contract c WHERE c.ctStatus IN :statuses")
    List<Contract> findByCtStatusIn(@Param("statuses") List<String> statuses);
}