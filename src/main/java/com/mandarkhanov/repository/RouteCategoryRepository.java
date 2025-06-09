package com.mandarkhanov.repository;

import com.mandarkhanov.model.RouteCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteCategoryRepository extends PagingAndSortingRepository<RouteCategory, Integer>, CrudRepository<RouteCategory, Integer> {
}