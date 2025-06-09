package com.mandarkhanov.repository;

import com.mandarkhanov.model.TrainType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTypeRepository extends PagingAndSortingRepository<TrainType, Integer>, CrudRepository<TrainType, Integer> {
}