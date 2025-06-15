package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Integer id;

    @NotNull
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "build_date")
    private LocalDate buildDate;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    @NotNull
    @Pattern(regexp = "^(в порядке|требует ремонта|в ремонте|списан)$",
            message = "Статус должен быть одним из следующих: в порядке, требует ремонта, в ремонте, списан")
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Formula("(select count(*) from schedules s where s.train_id = train_id)")
    private int tripsCount;

    @Formula("(select count(*) from maintenance m where m.train_id = train_id)")
    private int maintenanceCount;

    @Formula("(select count(*) from maintenance m where m.train_id = train_id and m.is_repair = true)")
    private int repairCount;
}