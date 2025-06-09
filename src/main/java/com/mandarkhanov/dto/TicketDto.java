package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {

    @NotNull
    private Integer scheduleId;

    @NotNull
    private Integer passengerId;

    @NotNull
    private Integer seatId;

    private Integer luggageId;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @Pattern(regexp = "^(забронирован|оплачен|возвращен|использован)$",
            message = "Статус билета должен быть одним из: забронирован, оплачен, возвращен, использован")
    private String ticketStatus;
}