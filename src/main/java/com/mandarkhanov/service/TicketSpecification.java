package com.mandarkhanov.service;

import com.mandarkhanov.model.Route;
import com.mandarkhanov.model.Schedule;
import com.mandarkhanov.model.Ticket;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class TicketSpecification {

    public static Specification<Ticket> hasPurchaseDateBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (from != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("purchaseDate"), from.atStartOfDay()));
            }
            if (to != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("purchaseDate"), to.atTime(LocalTime.MAX)));
            }
            return predicate;
        };
    }

    public static Specification<Ticket> hasStatus(String status) {
        if (status == null || status.isEmpty()) return null;
        return (root, query, cb) -> cb.equal(root.get("ticketStatus"), status);
    }

    public static Specification<Ticket> forSchedule(Integer scheduleId) {
        if (scheduleId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("schedule").get("id"), scheduleId);
    }

    public static Specification<Ticket> forRoute(Integer routeId) {
        if (routeId == null) return null;
        return (root, query, cb) -> {
            Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
            return cb.equal(scheduleJoin.get("route").get("id"), routeId);
        };
    }

    public static Specification<Ticket> forDepartureDate(LocalDate departureDate) {
        if (departureDate == null) return null;
        return (root, query, cb) -> {
            Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
            return cb.equal(cb.function("DATE", LocalDate.class, scheduleJoin.get("departureTime")), departureDate);
        };
    }

    public static Specification<Ticket> withRouteDistanceBetween(Integer min, Integer max) {
        if (min == null && max == null) return null;
        return (root, query, cb) -> {
            Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
            Join<Schedule, Route> routeJoin = scheduleJoin.join("route");
            Predicate predicate = cb.conjunction();
            if (min != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(routeJoin.get("distanceKm"), min));
            }
            if (max != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(routeJoin.get("distanceKm"), max));
            }
            return predicate;
        };
    }

    public static Specification<Ticket> hasPriceBetween(BigDecimal min, BigDecimal max) {
        if (min == null && max == null) return null;
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (min != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), min));
            }
            if (max != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), max));
            }
            return predicate;
        };
    }
}