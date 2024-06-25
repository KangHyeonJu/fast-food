package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessRepository extends JpaRepository<Process, String> {
    //pcCode로 pcName 찾기
    @Query("SELECT p.pcName FROM Process p WHERE p.pcCode = :pcCode")
    String findPcNameByPcCode(@Param("pcCode") String pcCode);
}
