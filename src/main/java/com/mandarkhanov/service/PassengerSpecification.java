package com.mandarkhanov.service;

import com.mandarkhanov.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PassengerSpecification {

    public static Specification<Passenger> forSchedule(Integer scheduleId) {
        if (scheduleId == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Ticket> ticketRoot = subquery.from(Ticket.class);
            subquery.select(ticketRoot.get("passenger").get("id"))
                    .where(cb.equal(ticketRoot.get("schedule").get("id"), scheduleId));
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Passenger> departedOnDate(LocalDate date) {
        if (date == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Ticket> ticketRoot = subquery.from(Ticket.class);
            Join<Ticket, Schedule> scheduleJoin = ticketRoot.join("schedule");
            subquery.select(ticketRoot.get("passenger").get("id"))
                    .where(cb.equal(cb.function("DATE", LocalDate.class, scheduleJoin.get("departureTime")), date));
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Passenger> departedAbroadOnDate(LocalDate date) {
        if (date == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Ticket> ticketRoot = subquery.from(Ticket.class);
            Join<Ticket, Schedule> scheduleJoin = ticketRoot.join("schedule");
            Join<Schedule, Route> routeJoin = scheduleJoin.join("route");
            Join<Route, RouteCategory> categoryJoin = routeJoin.join("category");

            Predicate datePredicate = cb.equal(cb.function("DATE", LocalDate.class, scheduleJoin.get("departureTime")), date);
            Predicate categoryPredicate = cb.equal(categoryJoin.get("name"), "международный");

            subquery.select(ticketRoot.get("passenger").get("id"))
                    .where(cb.and(datePredicate, categoryPredicate));
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Passenger> hasLuggage(Boolean hasLuggage) {
        if (hasLuggage == null) return null;
        return (root, query, cb) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Ticket> ticketRoot = subquery.from(Ticket.class);
            subquery.select(ticketRoot.get("passenger").get("id"));
            if (hasLuggage) {
                subquery.where(cb.isNotNull(ticketRoot.get("luggage")));
            } else {
                subquery.where(cb.isNull(ticketRoot.get("luggage")));
            }
            query.distinct(true);
            return root.get("id").in(subquery);
        };
    }

    public static Specification<Passenger> hasGender(Character gender) {
        if (gender == null) return null;
        return (root, query, cb) -> cb.equal(root.get("gender"), gender);
    }

    public static Specification<Passenger> isAgeBetween(Integer minAge, Integer maxAge) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minAge != null) {
                LocalDate maxBirthDate = LocalDate.now().minusYears(minAge);
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("birthDate"), maxBirthDate));
            }
            if (maxAge != null) {
                LocalDate minBirthDate = LocalDate.now().minusYears(maxAge + 1).plusDays(1);
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("birthDate"), minBirthDate));
            }
            return predicate;
        };
    }
}