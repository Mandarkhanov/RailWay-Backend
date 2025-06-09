package com.mandarkhanov.repository;

import com.mandarkhanov.model.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer>, CrudRepository<Schedule, Integer> {
}