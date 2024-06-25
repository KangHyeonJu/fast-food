package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, String> {


    @Query(value = "SELECT p FROM Process p WHERE p.pcCode LIKE %:pcCode%")
    List<Process> findByPcCode(@Param("pcCode") String pcCode);

    @Query(value = "SELECT p FROM Process p WHERE p.pcName LIKE %:pcName%")

    List<Process> findByPcName(@Param("pcName") String pcName);
    @Query(value = "SELECT p FROM Process p WHERE p.pcCnt LIKE %:pcCnt%")
    List<Process> findByPcCnt(@Param("pcCnt") String pcCnt);

    @Query(value = "SELECT p FROM Process p WHERE p.pcCode LIKE %:pcCode% AND p.pcName LIKE %:pcName% AND p.pcCnt LIKE %:pcCnt%")
    List<Process> findByPcCodeAndPcNameAndPcCnt(@Param("pcCode") String pcCode, @Param("pcName") String pcName, @Param("pcCnt") String pcCnt);

    @Query(value = "SELECT p FROM Process p WHERE p.pcCode LIKE %:pcCode% AND p.pcName LIKE %:pcName%")
    List<Process> findByPcCodeAndPcName(@Param("pcCode") String pcCode, @Param("pcName") String pcName);

    @Query(value = "SELECT p FROM Process p WHERE p.pcCode LIKE %:pcCode% AND p.pcCnt LIKE %:pcCnt%")
    List<Process> findByPcCodeAndPcCnt(@Param("pcCode") String pcCode, @Param("pcCnt") String pcCnt);

    @Query(value = "SELECT p FROM Process p WHERE p.pcCnt LIKE %:pcCnt% AND p.pcName LIKE %:pcName%")
    List<Process> findByPcCntAndPcName(@Param("pcCnt") String pcCnt, @Param("pcName") String pcName);


}
