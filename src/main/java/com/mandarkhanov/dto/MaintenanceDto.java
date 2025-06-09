package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaintenanceDto {

    @NotNull
    private Integer trainId;

    private Integer brigadeId;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    @Pattern(regexp = "^(плановый|рейсовый|аварийный|деповской|ТО-1|ТО-2|ТР-1|ТР-2|ТР-3|КР-1|КР-2)$",
            message = "Неверный тип технического обслуживания")
    private String type;

    private String result;

    @NotNull
    private Boolean isRepair;
}