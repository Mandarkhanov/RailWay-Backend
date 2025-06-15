package com.mandarkhanov.service;

import com.mandarkhanov.model.Schedule;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ScheduleSpecification {

    public static Specification<Schedule> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minPrice != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("basePrice"), minPrice));
            }
            if (maxPrice != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("basePrice"), maxPrice));
            }
            return predicate;
        };
    }

    public static Specification<Schedule> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("trainStatus"), status);
        };
    }

    public static Specification<Schedule> forRoute(Integer routeId) {
        return (root, query, cb) -> {
            if (routeId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("route").get("id"), routeId);
        };
    }

    public static Specification<Schedule> hasReturnedTicketsGreaterThanOrEqual(Integer count) {
        return (root, query, cb) -> {
            if (count == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("returnedTickets"), count);
        };
    }
}