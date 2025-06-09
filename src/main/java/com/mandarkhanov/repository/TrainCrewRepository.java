package com.mandarkhanov.repository;

import com.mandarkhanov.model.TrainCrew;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainCrewRepository extends PagingAndSortingRepository<TrainCrew, Integer>, CrudRepository<TrainCrew, Integer> {
}