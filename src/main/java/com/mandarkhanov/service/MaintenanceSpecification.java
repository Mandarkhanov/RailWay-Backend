package com.mandarkhanov.service;

import com.mandarkhanov.model.Maintenance;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MaintenanceSpecification {

    public static Specification<Maintenance> forTrain(Integer trainId) {
        return (root, query, cb) -> {
            if (trainId == null) return cb.conjunction();
            return cb.equal(root.get("train").get("id"), trainId);
        };
    }

    public static Specification<Maintenance> inDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (dateFrom != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startDate"), dateFrom));
            }
            if (dateTo != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("startDate"), dateTo));
            }
            return predicate;
        };
    }
}