package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "route_stops",
       uniqueConstraints = {
            @UniqueConstraint(columnNames = {"route_id", "stop_order"}),
            @UniqueConstraint(columnNames = {"route_id", "station_id"})
       })
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_stop_id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Route route;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Station station;

    @Positive
    @NotNull
    @Column(name = "stop_order")
    private Integer stopOrder;

    @Column(name = "arrival_offset")
    private Integer arrivalOffset;

    @Column(name = "departure_offset")
    private Integer departureOffset;

    @Column(name = "platform", length = 10)
    private String platform;
}
