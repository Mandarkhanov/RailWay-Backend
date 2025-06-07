package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "train_types")
public class TrainType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Integer id;

    @NotNull
    @Pattern(regexp = "^(скорый|пассажирский|грузовой)$",
             message = "Тип поезда должен быть одним из следующих: скорый, пассажирский, грузовой")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}
