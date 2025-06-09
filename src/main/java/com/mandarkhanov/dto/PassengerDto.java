package com.mandarkhanov.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerDto {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String middleName;

    @Past
    private LocalDate birthDate;

    @Pattern(regexp = "^[МЖ]$", message = "Пол должен быть 'М' или 'Ж'")
    private String gender;

    @NotBlank
    @Size(max = 10)
    private String passportSeries;

    @NotBlank
    @Size(max = 20)
    private String passportNumber;

    @Size(max = 20)
    private String phoneNumber;

    @Email
    @Size(max = 100)
    private String email;
}