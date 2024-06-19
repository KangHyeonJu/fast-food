package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, String> {
}
