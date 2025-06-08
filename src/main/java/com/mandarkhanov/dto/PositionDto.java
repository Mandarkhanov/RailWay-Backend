package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PositionDto {
    @NotBlank(message = "Название должности не может быть пустым")
    @Size(max = 100)
    private String name;

    @NotNull(message = "ID не может быть пустым")
    private Integer departmentId;

    @PositiveOrZero
    private BigDecimal minSalary;

    @PositiveOrZero
    private BigDecimal maxSalary;

    private String description;
}
