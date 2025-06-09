package com.mandarkhanov.repository;

import com.mandarkhanov.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer>, CrudRepository<Seat, Integer> {
}