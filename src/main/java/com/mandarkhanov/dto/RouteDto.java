package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RouteDto {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Integer startStationId;

    @NotNull
    private Integer endStationId;

    @Positive
    private Integer distanceKm;

    private Integer categoryId;
}