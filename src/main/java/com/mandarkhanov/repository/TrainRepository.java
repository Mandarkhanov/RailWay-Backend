package com.mandarkhanov.repository;

import com.mandarkhanov.model.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainRepository extends PagingAndSortingRepository<Train, Integer>, CrudRepository<Train, Integer> {
}
