package com.mandarkhanov.repository;

import com.mandarkhanov.model.Seat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer>, CrudRepository<Seat, Integer>, JpaSpecificationExecutor<Seat> {

    /**
     * Находит все доступные места для конкретного рейса (schedule).
     * @param scheduleId ID рейса.
     * @return Список свободных мест.
     */
    @Query("SELECT s FROM Seat s " +
            "JOIN s.car c " +
            "JOIN c.train t " +
            "JOIN Schedule sch ON sch.train.id = t.id " +
            "WHERE sch.id = :scheduleId AND s.isAvailable = true")
    List<Seat> findAvailableSeatsByScheduleId(@Param("scheduleId") Integer scheduleId);
}