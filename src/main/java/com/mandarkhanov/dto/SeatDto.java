package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeatDto {

    @NotNull
    private Integer carId;

    @NotBlank
    @Size(max = 10)
    private String seatNumber;

    @Pattern(regexp = "^(нижнее|верхнее|боковое нижнее|боковое верхнее|у окна|у прохода)$",
            message = "Неверный тип места")
    private String seatType;

    @NotNull
    private Boolean isAvailable;

    private String features;
}