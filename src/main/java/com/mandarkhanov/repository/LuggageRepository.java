package com.mandarkhanov.repository;

import com.mandarkhanov.model.Luggage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuggageRepository extends PagingAndSortingRepository<Luggage, Integer>, CrudRepository<Luggage, Integer> {
}