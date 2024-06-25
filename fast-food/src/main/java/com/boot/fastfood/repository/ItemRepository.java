package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Items;
import com.boot.fastfood.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items, String> {


    List<Items> findByItCodeIn(List<String> itCode);
}
