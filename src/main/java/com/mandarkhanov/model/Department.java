package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <-- Добавить импорт
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer id;

    @NotNull
    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee manager;
}