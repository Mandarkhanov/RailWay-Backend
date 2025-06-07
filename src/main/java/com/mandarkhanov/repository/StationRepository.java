package com.mandarkhanov.repository;

import com.mandarkhanov.model.Station;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StationRepository extends PagingAndSortingRepository<Station, Integer>, CrudRepository<Station, Integer> {
}
