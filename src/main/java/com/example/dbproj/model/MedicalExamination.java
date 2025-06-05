package com.example.dbproj.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "medical_examinations")
public class MedicalExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examination_id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee employee;

    @NotNull
    @Column(name = "examination_date", nullable = false)
    private LocalDate examinationDate;

    @NotNull
    @Column(name = "result", nullable = false)
    private Boolean result;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}