package com.mandarkhanov.repository;

import com.mandarkhanov.model.Route;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteRepository extends PagingAndSortingRepository<Route, Integer>, CrudRepository<Route, Integer>, JpaSpecificationExecutor<Route> {
}