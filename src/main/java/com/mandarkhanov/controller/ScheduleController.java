package com.mandarkhanov.controller;

import com.mandarkhanov.dto.ScheduleDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Route;
import com.mandarkhanov.model.Schedule;
import com.mandarkhanov.model.Train;
import com.mandarkhanov.model.TrainType;
import com.mandarkhanov.repository.RouteRepository;
import com.mandarkhanov.repository.ScheduleRepository;
import com.mandarkhanov.repository.TrainRepository;
import com.mandarkhanov.repository.TrainTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private TrainTypeRepository trainTypeRepository;
    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public Iterable<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + id + " не найден"));
        return ResponseEntity.ok(schedule);
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