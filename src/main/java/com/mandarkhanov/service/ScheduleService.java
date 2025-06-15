package com.mandarkhanov.service;

import com.mandarkhanov.model.Schedule;
import com.mandarkhanov.model.Seat;
import com.mandarkhanov.repository.ScheduleRepository;
import com.mandarkhanov.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    public List<Schedule> findSchedulesByStations(String fromStationName, String toStationName) {
        return scheduleRepository.findSchedulesByStationNames(fromStationName, toStationName);
    }

    public List<Seat> findAvailableSeats(Integer scheduleId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new com.mandarkhanov.exception.ResourceNotFoundException("Рейс с ID " + scheduleId + " не найден"));
        return seatRepository.findAvailableSeatsByScheduleId(scheduleId);
    }
}