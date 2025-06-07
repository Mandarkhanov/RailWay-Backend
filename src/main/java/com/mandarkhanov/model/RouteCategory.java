package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "route_categories")
public class RouteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @NotNull
    @Pattern(regexp = "^(внутренний|международный|туристический|специальный|пригородный)$",
             message = "Категория маршрута должа быть одной из следующих: внутренний, международный, туристический, специальный, пригородный")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
