package com.mandarkhanov.service;

import com.mandarkhanov.model.Maintenance;
import com.mandarkhanov.model.Route;
import com.mandarkhanov.model.Schedule;
import com.mandarkhanov.model.Train;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainSpecification {

    public static Specification<Train> forRoute(Integer routeId) {
        if (routeId == null) {
            return null;
        }
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Schedule> scheduleRoot = subquery.from(Schedule.class);
            subquery.select(scheduleRoot.get("train").get("id"))
                    .where(cb.equal(scheduleRoot.get("route").get("id"), routeId));
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> withRouteDistanceBetween(Integer minDistance, Integer maxDistance) {
        if (minDistance == null && maxDistance == null) {
            return null;
        }
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Schedule> scheduleRoot = subquery.from(Schedule.class);
            Join<Schedule, Route> routeJoin = scheduleRoot.join("route");
            subquery.select(scheduleRoot.get("train").get("id"));

            Predicate distancePredicate = cb.conjunction();
            if (minDistance != null) {
                distancePredicate = cb.and(distancePredicate, cb.greaterThanOrEqualTo(routeJoin.get("distanceKm"), minDistance));
            }
            if (maxDistance != null) {
                distancePredicate = cb.and(distancePredicate, cb.lessThanOrEqualTo(routeJoin.get("distanceKm"), maxDistance));
            }
            subquery.where(distancePredicate);
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> hasStation(Integer stationId) {
        if (stationId == null) return null;
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Schedule> scheduleRoot = subquery.from(Schedule.class);
            Join<Schedule, Route> routeJoin = scheduleRoot.join("route");
            subquery.select(scheduleRoot.get("train").get("id"))
                    .where(criteriaBuilder.or(
                            criteriaBuilder.equal(routeJoin.get("startStation").get("id"), stationId),
                            criteriaBuilder.equal(routeJoin.get("endStation").get("id"), stationId)
                    ));
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> hasMinTrips(Integer minTrips) {
        if (minTrips == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("tripsCount"), minTrips);
    }

    public static Specification<Train> arrivedAtStationBefore(Integer stationId, LocalDateTime arrivalTimeTo) {
        if (stationId == null || arrivalTimeTo == null) return null;
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Schedule> scheduleRoot = subquery.from(Schedule.class);
            Join<Schedule, Route> routeJoin = scheduleRoot.join("route");
            Predicate stationPredicate = criteriaBuilder.equal(routeJoin.get("endStation").get("id"), stationId);
            Predicate timePredicate = criteriaBuilder.lessThanOrEqualTo(scheduleRoot.get("arrivalTime"), arrivalTimeTo);
            subquery.select(scheduleRoot.get("train").get("id"))
                    .where(criteriaBuilder.and(stationPredicate, timePredicate));
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> hadPlannedMaintenanceInDateRange(LocalDate from, LocalDate to) {
        if (from == null && to == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Maintenance> maintenanceRoot = subquery.from(Maintenance.class);
            subquery.select(maintenanceRoot.get("train").get("id"));
            Predicate predicate = cb.equal(maintenanceRoot.get("type"), "плановый");
            if (from != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(maintenanceRoot.get("startDate").as(LocalDate.class), from));
            }
            if (to != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(maintenanceRoot.get("startDate").as(LocalDate.class), to));
            }
            subquery.where(predicate);
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> hasRepairInRange(LocalDate from, LocalDate to) {
        if (from == null && to == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Maintenance> maintenanceRoot = subquery.from(Maintenance.class);
            subquery.select(maintenanceRoot.get("train").get("id"));
            Predicate predicate = cb.isTrue(maintenanceRoot.get("isRepair"));
            if (from != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(maintenanceRoot.get("startDate").as(LocalDate.class), from));
            }
            if (to != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(maintenanceRoot.get("startDate").as(LocalDate.class), to));
            }
            subquery.where(predicate);
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Train> hasRepairsCount(Integer count) {
        if (count == null) return null;
        return (root, query, cb) -> cb.equal(root.get("repairCount"), count);
    }

    public static Specification<Train> isAgeBetween(Integer minAge, Integer maxAge) {
        if (minAge == null && maxAge == null) return null;
        return (root, query, cb) -> {
            Specification<Train> spec = Specification.where(null);
            if (minAge != null) {
                LocalDate maxBuildDate = LocalDate.now().minusYears(minAge);
                spec = spec.and((r, q, c) -> c.lessThanOrEqualTo(r.get("buildDate"), maxBuildDate));
            }
            if (maxAge != null) {
                LocalDate minBuildDate = LocalDate.now().minusYears(maxAge + 1).plusDays(1);
                spec = spec.and((r, q, c) -> c.greaterThanOrEqualTo(r.get("buildDate"), minBuildDate));
            }
            return spec.toPredicate(root, query, cb);
        };
    }
}