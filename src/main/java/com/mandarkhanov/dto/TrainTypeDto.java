package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TrainTypeDto {
    @NotBlank
    @Pattern(regexp = "^(скорый|пассажирский|грузовой)$",
            message = "Тип поезда должен быть одним из: скорый, пассажирский, грузовой")
    private String name;
}