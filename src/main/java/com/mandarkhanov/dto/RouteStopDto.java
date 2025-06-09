package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RouteStopDto {

    @NotNull
    private Integer routeId;

    @NotNull
    private Integer stationId;

    @NotNull
    @Positive
    private Integer stopOrder;

    private Integer arrivalOffset;

    private Integer departureOffset;

    @Size(max = 10)
    private String platform;
}