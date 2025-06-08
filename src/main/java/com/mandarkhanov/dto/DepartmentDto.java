package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDto {
    @NotBlank(message = "Название отдела не может быть пустым")
    @Size(max = 100)
    private String name;

    private String description;
}
