package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Train train;

    @NotNull
    @Column(name = "train_number", nullable = false, length = 10)
    private String trainNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TrainType type;

    @NotNull
    @Pattern(regexp = "^(по расписанию|задержан|отменен|выполнен|в пути)$",
            message = "Статус поезда должен быть одним из следующих: по расписанию, задержан, отменен, выполнен, в пути")
    @Column(name = "train_status", nullable = false, length = 50)
    private String trainStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Route route;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull
    @PositiveOrZero
    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Formula("(SELECT COUNT(*) FROM tickets t WHERE t.schedule_id = schedule_id)")
    private int totalTickets;

    @Formula("(SELECT COUNT(*) FROM tickets t WHERE t.schedule_id = schedule_id AND t.ticket_status = 'оплачен')")
    private int paidTickets;

    @Formula("(SELECT COUNT(*) FROM tickets t WHERE t.schedule_id = schedule_id AND t.ticket_status = 'забронирован')")
    private int bookedTickets;

    @Formula("(SELECT COUNT(*) FROM tickets t WHERE t.schedule_id = schedule_id AND t.ticket_status = 'возвращен')")
    private int returnedTickets;

    @Formula("(SELECT COUNT(*) FROM tickets t WHERE t.schedule_id = schedule_id AND t.ticket_status = 'использован')")
    private int usedTickets;
}