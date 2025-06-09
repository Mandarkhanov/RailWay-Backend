package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"schedule_id", "seat_id"})
        })
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Schedule schedule;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Passenger passenger;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Seat seat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luggage_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Luggage luggage;

    @CreationTimestamp
    @Column(name = "purchase_date", nullable = false, updatable = false)
    private LocalDateTime purchaseDate;

    @NotNull
    @PositiveOrZero
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Pattern(regexp = "^(забронирован|оплачен|возвращен|использован)$",
            message = "Статус билета должен быть одним из следующих: забронирован, оплачен, возвращен, использован")
    @Column(name = "ticket_status", nullable = false, length = 50)
    private String ticketStatus;
}