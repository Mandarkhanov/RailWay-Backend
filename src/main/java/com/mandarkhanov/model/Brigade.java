package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "brigades")
public class Brigade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brigade_id")
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee manager;

    @Formula("(SELECT COUNT(*) FROM employee_brigade eb WHERE eb.brigade_id = brigade_id)")
    private int employeeCount;

    @Formula("(SELECT COALESCE(SUM(e.salary), 0) FROM employees e JOIN employee_brigade eb ON e.employee_id = eb.employee_id WHERE eb.brigade_id = brigade_id)")
    private BigDecimal totalSalary;

    @Formula("(SELECT AVG(e.salary) FROM employees e JOIN employee_brigade eb ON e.employee_id = eb.employee_id WHERE eb.brigade_id = brigade_id)")
    private BigDecimal averageSalary;
}