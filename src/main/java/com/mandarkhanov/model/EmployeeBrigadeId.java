package com.mandarkhanov.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmployeeBrigadeId implements Serializable {
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "brigade_id")
    private Integer brigadeId;
}
