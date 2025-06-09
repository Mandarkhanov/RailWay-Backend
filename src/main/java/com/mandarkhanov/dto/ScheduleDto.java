package com.mandarkhanov.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private Integer trainId;

    @NotBlank
    @Size(max = 10)
    private String trainNumber;

    private Integer typeId;

    @NotBlank
    @Pattern(regexp = "^(по расписанию|задержан|отменен|выполнен|в пути)$",
            message = "Статус поезда должен быть одним из: по расписанию, задержан, отменен, выполнен, в пути")
    private String trainStatus;

    private Integer routeId;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @PositiveOrZero
    private BigDecimal basePrice;
}