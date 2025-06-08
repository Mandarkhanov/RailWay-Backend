package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrigadeDto {
    @NotBlank(message = "Название блигады не должно быть пустым")
    @Size(max = 100)
    private String name;

    private Integer departmentId;

    private Integer managerId;
}
