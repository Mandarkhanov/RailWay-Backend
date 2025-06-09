package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brigade_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Brigade brigade;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @Pattern(regexp = "^(плановый|рейсовый|аварийный|деповской|ТО-1|ТО-2|ТР-1|ТР-2|ТР-3|КР-1|КР-2)$",
            message = "Неверный тип технического обслуживания")
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

    @NotNull
    @Column(name = "is_repair", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRepair;
}