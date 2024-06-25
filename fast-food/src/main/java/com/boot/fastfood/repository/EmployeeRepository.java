package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Optional<Employee> findByEmCode(String emCode);

    Employee findByEmName(String emName);

    List<Employee> findByEmCodeIn(List<String> emCode);
}
