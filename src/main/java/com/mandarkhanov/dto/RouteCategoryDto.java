package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RouteCategoryDto {

    @NotBlank
    @Pattern(regexp = "^(внутренний|международный|туристический|специальный|пригородный)$",
            message = "Категория маршрута должа быть одной из: внутренний, международный, туристический, специальный, пригородный")
    private String name;
}