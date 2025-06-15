package com.mandarkhanov.repository;

import com.mandarkhanov.model.TrainCrew;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainCrewRepository extends PagingAndSortingRepository<TrainCrew, Integer>, CrudRepository<TrainCrew, Integer> {

    List<TrainCrew> findByScheduleIdIn(List<Integer> scheduleIds);
}