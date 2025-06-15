package com.mandarkhanov.repository;

import com.mandarkhanov.model.Schedule;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer>, CrudRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

    /**
     * Находит рейсы по названию станции отправления и станции прибытия.
     * @param fromStationName Название станции отправления.
     * @param toStationName Название станции прибытия.
     * @return Список найденных рейсов.
     */
    @Query("SELECT s FROM Schedule s JOIN s.route r WHERE r.startStation.name = :from AND r.endStation.name = :to")
    List<Schedule> findSchedulesByStationNames(@Param("from") String fromStationName, @Param("to") String toStationName);

    List<Schedule> findByTrainId(Integer trainId);
}