package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, String> {


    Vendor findByVdCode(String vdCode);

    List<Vendor> findByVdCodeIn(List<String> vdCode);

    List<Vendor> findByVdName(String vdName);

    List<Vendor> findByMaterials_MtName(String mtName);

    List<Vendor> findByVdCodeAndVdName(String vdCode, String vdName);

    List<Vendor> findByVdCodeAndMaterials_MtName(String vdCode, String mtName);

    List<Vendor> findByVdNameAndMaterials_MtName(String vdName, String mtName);

    // Add more methods for additional search criteria if needed

}
