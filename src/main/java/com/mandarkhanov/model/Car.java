package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer id;

    @NotNull
    @Column(name = "car_number", nullable = false, unique = true, length = 20)
    private String carNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Train train;

    @NotNull
    @Pattern(regexp = "^(плацкарт|купе|спальный|сидячий|ресторан|багажный)$",
            message = "Тип вагона должен быть одним из следующих: плацкарт, купе, спальный, сидячий, ресторан, багажный")
    @Column(name = "car_type", nullable = false, length = 50)
    private String carType;

    @PositiveOrZero
    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "build_date")
    private LocalDate buildDate;

    @Pattern(regexp = "^(в эксплуатации|в ремонте|списан)$",
            message = "Статус должен быть одним из следующих: в эксплуатации, в ремонте, списан")
    @Column(name = "status", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'в эксплуатации'")
    private String status;
}