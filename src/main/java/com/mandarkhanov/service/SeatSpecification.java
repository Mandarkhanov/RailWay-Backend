package com.mandarkhanov.service;

import com.mandarkhanov.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class SeatSpecification {

    @SuppressWarnings("unchecked")
    private static <Z, X> Join<Z, X> getOrCreateJoin(From<Z, ?> from, String attributeName, JoinType joinType) {
        for (Join<?, ?> join : from.getJoins()) {
            if (join.getAttribute().getName().equals(attributeName)) {
                return (Join<Z, X>) join;
            }
        }
        return from.join(attributeName, joinType);
    }

    public static Specification<Seat> hasAvailability(Boolean isAvailable) {
        if (isAvailable == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("isAvailable"), isAvailable);
    }

    private static Join<Seat, Schedule> getScheduleJoin(Root<Seat> root) {
        Join<Seat, Car> carJoin = getOrCreateJoin(root, "car", JoinType.INNER);
        Join<Seat, Object> trainJoin = getOrCreateJoin(carJoin, "train", JoinType.INNER);
        return getOrCreateJoin(trainJoin, "schedules", JoinType.INNER);
    }

    public static Specification<Seat> forSchedule(Integer scheduleId) {
        if (scheduleId == null) return null;
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Seat, Schedule> scheduleJoin = getScheduleJoin(root);
            return cb.equal(scheduleJoin.get("id"), scheduleId);
        };
    }

    public static Specification<Seat> forRoute(Integer routeId) {
        if (routeId == null) return null;
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Seat, Schedule> scheduleJoin = getScheduleJoin(root);
            Join<Seat, Object> routeJoin = getOrCreateJoin(scheduleJoin, "route", JoinType.INNER);
            return cb.equal(routeJoin.get("id"), routeId);
        };
    }

    public static Specification<Seat> forDepartureDate(LocalDate departureDate) {
        if (departureDate == null) return null;
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Seat, Schedule> scheduleJoin = getScheduleJoin(root);
            return cb.equal(cb.function("DATE", LocalDate.class, scheduleJoin.get("departureTime")), departureDate);
        };
    }
}