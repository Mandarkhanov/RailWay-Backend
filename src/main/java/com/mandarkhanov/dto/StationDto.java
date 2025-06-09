package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StationDto {
    @NotBlank(message = "Название станции не может быть пустым")
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String address;

    @Size(max = 100)
    private String region;
}