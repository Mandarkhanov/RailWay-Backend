package com.mandarkhanov.repository;

import com.mandarkhanov.model.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends PagingAndSortingRepository<Passenger, Integer>, CrudRepository<Passenger, Integer> {
}