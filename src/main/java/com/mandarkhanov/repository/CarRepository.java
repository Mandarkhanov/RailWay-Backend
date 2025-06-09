package com.mandarkhanov.repository;

import com.mandarkhanov.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Integer>, CrudRepository<Car, Integer> {
}