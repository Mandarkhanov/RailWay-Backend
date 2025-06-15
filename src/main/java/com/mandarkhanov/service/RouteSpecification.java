package com.mandarkhanov.service;

import com.mandarkhanov.model.Route;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {

    public static Specification<Route> hasCategory(Integer categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Route> toStation(Integer endStationId) {
        return (root, query, cb) -> {
            if (endStationId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("endStation").get("id"), endStationId);
        };
    }
}