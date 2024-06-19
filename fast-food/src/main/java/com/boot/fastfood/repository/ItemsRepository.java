package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, String> {
//    Items findByItCode(String itCode);

    Items findByItName(String 양배추즙);
}