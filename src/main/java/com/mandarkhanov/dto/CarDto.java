package com.mandarkhanov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDto {

    @NotBlank(message = "Номер вагона не может быть пустым")
    @Size(max = 20)
    private String carNumber;

    private Integer trainId;

    @NotBlank(message = "Тип вагона не может быть пустым")
    @Pattern(regexp = "^(плацкарт|купе|спальный|сидячий|ресторан|багажный)$",
            message = "Тип вагона должен быть одним из: плацкарт, купе, спальный, сидячий, ресторан, багажный")
    private String carType;

    private Integer capacity;

    private LocalDate buildDate;

    @Pattern(regexp = "^(в эксплуатации|в ремонте|списан)$",
            message = "Статус должен быть одним из: в эксплуатации, в ремонте, списан")
    private String status;
}