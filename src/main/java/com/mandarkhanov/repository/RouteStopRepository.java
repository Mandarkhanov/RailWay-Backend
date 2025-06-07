package com.mandarkhanov.repository;

import com.mandarkhanov.model.RouteStop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteStopRepository extends PagingAndSortingRepository<RouteStop, Integer>, CrudRepository<RouteStop, Integer> {
}
