package com.mandarkhanov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "passengers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"passport_series", "passport_number"})
        })
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender", length = 1)
    private Character gender;

    @NotNull
    @Column(name = "passport_series", nullable = false, length = 10)
    private String passportSeries;

    @NotNull
    @Column(name = "passport_number", nullable = false, length = 20)
    private String passportNumber;

    @Column(name = "phone_number", unique = true, length = 20)
    private String phoneNumber;

    @Email
    @Column(name = "email", unique = true, length = 100)
    private String email;
}