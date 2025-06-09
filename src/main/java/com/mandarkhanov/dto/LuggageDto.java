package com.mandarkhanov.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LuggageDto {

    @PositiveOrZero
    private BigDecimal weightKg;

    @PositiveOrZero
    private Integer pieces;

    @Pattern(regexp = "^(зарегистрирован|в пути|выдан|потерян)$",
            message = "Статус багажа должен быть одним из: зарегистрирован, в пути, выдан, потерян")
    private String status;
}