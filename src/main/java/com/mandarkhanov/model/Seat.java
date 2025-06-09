package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"car_id", "seat_number"})
        })
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Car car;

    @NotNull
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Pattern(regexp = "^(нижнее|верхнее|боковое нижнее|боковое верхнее|у окна|у прохода)$",
            message = "Неверный тип места")
    @Column(name = "seat_type", length = 50)
    private String seatType;

    @NotNull
    @Column(name = "is_available", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable;

    @Column(name = "features", columnDefinition = "TEXT")
    private String features;
}