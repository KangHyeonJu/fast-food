package com.boot.fastfood.repository;


import com.boot.fastfood.entity.Releases;
import com.boot.fastfood.entity.Works;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleasesRepository extends JpaRepository<Releases, String> {

    Releases findByWorks(Works work);
}
