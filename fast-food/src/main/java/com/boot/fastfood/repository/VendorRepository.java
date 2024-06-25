package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, String> {


    Vendor findByVdCode(String vdCode);

    List<Vendor> findByVdCodeIn(List<String> vdCode);
}
