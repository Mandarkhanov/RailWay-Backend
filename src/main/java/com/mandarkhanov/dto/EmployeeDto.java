package com.mandarkhanov.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeDto {
    @NotBlank(message = "Имя сотрудника не может быть пустым")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Фамилия сотрудника не может быть пустым")
    @Size(max = 50)
    private String lastName;

    @Past
    private LocalDate birthDate;

    @Pattern(regexp = "^[МЖ]$", message = "Пол должен быть 'М' или 'Ж'")
    @Size(min = 1, max = 1)
    private String gender;

    @NotNull
    @PastOrPresent
    private LocalDate hireDate;

    @NotNull
    private Integer positionId;

    @PositiveOrZero
    private BigDecimal salary;

    @PositiveOrZero
    private Integer childrenCount;

    @NotNull
    private Boolean isActive;
}