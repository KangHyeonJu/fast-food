package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Items, String> {

}
