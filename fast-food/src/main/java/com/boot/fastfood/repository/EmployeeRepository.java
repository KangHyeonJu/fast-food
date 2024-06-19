package com.boot.fastfood.repository;

import com.boot.fastfood.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByEmCode(String emCode);
}
