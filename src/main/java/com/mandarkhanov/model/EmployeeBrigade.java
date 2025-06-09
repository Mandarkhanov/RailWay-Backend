package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "employee_brigade")
public class EmployeeBrigade {
    @EmbeddedId
    private EmployeeBrigadeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("brigadeId")
    @JoinColumn(name = "brigade_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Brigade brigade;

    @CreationTimestamp
    @Column(name = "assigment_date", nullable = false, updatable = false)
    private LocalDate assigmentDate;
}
