package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "luggage")
public class Luggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "luggage_id")
    private Integer id;

    @PositiveOrZero
    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @PositiveOrZero
    @Column(name = "pieces")
    private Integer pieces;

    @Pattern(regexp = "^(зарегистрирован|в пути|выдан|потерян)$",
            message = "Статус багажа должен быть одним из следующих: зарегистрирован, в пути, выдан, потерян")
    @Column(name = "status", length = 50)
    private String status;
}