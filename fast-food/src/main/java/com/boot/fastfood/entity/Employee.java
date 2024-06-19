package com.boot.fastfood.entity;

import com.boot.fastfood.dto.ClientDto;
import com.boot.fastfood.dto.EmployeeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(name = "emCode")
    private String emCode;

    @Column(name = "emName")
    private String emName;

    @Column(name = "emCon")
    private int emCon;

    //직원 등록
    public static Employee createEmployee(EmployeeDto employeeDto){
        return Employee.builder()
                .emCode(employeeDto.getEmCode())
                .emName(employeeDto.getEmName())
                .emCon(employeeDto.getEmCon())
                .build();
    }
}
