package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalExaminationDto {
    @NotNull
    private Integer employeeId;

    @NotNull
    private LocalDate examinationDate;

    @NotNull
    private Boolean result;

    private String notes;
}
