package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "region", length = 100)
    private String region;
}
