package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender", length = 1)
    private Character gender;

    @NotNull
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position position;

    @PositiveOrZero
    @Column(name = "salary")
    private BigDecimal salary;

    @PositiveOrZero
    @Column(name = "children_count", columnDefinition = "INTEDER DEFAULT 0")
    private Integer childrenCount;

    @NotNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE NOT NULL")
    private Boolean isActive;
}
