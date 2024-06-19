package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Materials;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Materials, String> {
}
