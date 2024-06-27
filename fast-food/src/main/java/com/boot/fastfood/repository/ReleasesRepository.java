package com.boot.fastfood.repository;


import com.boot.fastfood.entity.Materials;
import com.boot.fastfood.entity.Releases;
import com.boot.fastfood.entity.Warehousing;
import com.boot.fastfood.entity.Works;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleasesRepository extends JpaRepository<Releases, String> {

    List<Releases> findByRsCodeIn(List<String> rsCode);

    Releases findByWorksAndMaterials(Works work, Materials materials);
}
