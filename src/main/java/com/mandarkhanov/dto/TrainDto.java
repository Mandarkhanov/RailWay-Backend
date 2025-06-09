package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainDto {
    @NotBlank
    @Size(max = 100)
    private String model;

    private LocalDate buildDate;

    private LocalDate lastMaintenanceDate;

    @NotBlank
    @Pattern(regexp = "^(в порядке|требует ремонта|в ремонте|списан)$",
            message = "Статус должен быть одним из: в порядке, требует ремонта, в ремонте, списан")
    private String status;
}