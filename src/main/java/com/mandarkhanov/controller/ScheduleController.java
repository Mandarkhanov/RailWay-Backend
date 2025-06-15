package com.mandarkhanov.controller;

import com.mandarkhanov.dto.ScheduleDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.*;
import com.mandarkhanov.repository.RouteRepository;
import com.mandarkhanov.repository.ScheduleRepository;
import com.mandarkhanov.repository.TrainRepository;
import com.mandarkhanov.repository.TrainTypeRepository;
import com.mandarkhanov.service.ScheduleService;
import com.mandarkhanov.service.ScheduleSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private TrainTypeRepository trainTypeRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Iterable<Schedule> getAll(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String trainStatus,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minReturnedTickets
    ) {
        Specification<Schedule> spec = Specification.where(ScheduleSpecification.hasPriceBetween(minPrice, maxPrice))
                .and(ScheduleSpecification.hasStatus(trainStatus))
                .and(ScheduleSpecification.forRoute(routeId))
                .and(ScheduleSpecification.hasReturnedTicketsGreaterThanOrEqual(minReturnedTickets));
        return scheduleRepository.findAll(spec);
    }

    @GetMapping("/count")
    public Map<String, Long> getCount(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String trainStatus,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minReturnedTickets
    ) {
        Specification<Schedule> spec = Specification.where(ScheduleSpecification.hasPriceBetween(minPrice, maxPrice))
                .and(ScheduleSpecification.hasStatus(trainStatus))
                .and(ScheduleSpecification.forRoute(routeId))
                .and(ScheduleSpecification.hasReturnedTicketsGreaterThanOrEqual(minReturnedTickets));
        return Map.of("count", scheduleRepository.count(spec));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + id + " не найден"));
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Schedule>> searchSchedules(
            @RequestParam("fromStationName") String fromStationName,
            @RequestParam("toStationName") String toStationName) {
        List<Schedule> schedules = scheduleService.findSchedulesByStations(fromStationName, toStationName);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}/seats/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Integer id) {
        List<Seat> seats = scheduleService.findAvailableSeats(id);
        return ResponseEntity.ok(seats);
    }

    @PostMapping
    public Schedule create(@Valid @RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        updateScheduleFromDto(schedule, scheduleDto);
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@PathVariable Integer id, @Valid @RequestBody ScheduleDto scheduleDetails) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + id + " не найден"));
        updateScheduleFromDto(schedule, scheduleDetails);
        final Schedule updatedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + id + " не найден"));
        scheduleRepository.delete(schedule);
        return ResponseEntity.noContent().build();
    }

    private void updateScheduleFromDto(Schedule schedule, ScheduleDto dto) {
        schedule.setTrainNumber(dto.getTrainNumber());
        schedule.setTrainStatus(dto.getTrainStatus());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setBasePrice(dto.getBasePrice());

        if (dto.getTrainId() != null) {
            Train train = trainRepository.findById(dto.getTrainId())
                    .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + dto.getTrainId() + " не найден"));
            schedule.setTrain(train);
        } else {
            schedule.setTrain(null);
        }

        if (dto.getTypeId() != null) {
            TrainType type = trainTypeRepository.findById(dto.getTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Тип поезда с ID " + dto.getTypeId() + " не найден"));
            schedule.setType(type);
        } else {
            schedule.setType(null);
        }

        if (dto.getRouteId() != null) {
            Route route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + dto.getRouteId() + " не найден"));
            schedule.setRoute(route);
        } else {
            schedule.setRoute(null);
        }
    }
}