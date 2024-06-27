package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Optional<Employee> findByEmCode(String emCode);

    Employee findByEmName(String emName);


}
