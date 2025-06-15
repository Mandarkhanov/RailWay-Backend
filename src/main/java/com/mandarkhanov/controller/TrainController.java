package com.mandarkhanov.controller;

import com.mandarkhanov.dto.TrainDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.Train;
import com.mandarkhanov.repository.TrainRepository;
import com.mandarkhanov.service.TrainPersonnelService;
import com.mandarkhanov.service.TrainSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainPersonnelService trainPersonnelService;

    @GetMapping
    public Iterable<Train> getAll(
            @RequestParam(required = false) Integer stationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTimeTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintPlanFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintPlanTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintRepairFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintRepairTo,
            @RequestParam(required = false) Integer repairCount,
            @RequestParam(required = false) Integer minTrips,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minDistance,
            @RequestParam(required = false) Integer maxDistance
    ) {
        Specification<Train> spec = createSpecification(stationId, arrivalTimeTo, maintPlanFrom, maintPlanTo, maintRepairFrom, maintRepairTo, repairCount, minTrips, minAge, maxAge, routeId, minDistance, maxDistance);
        return trainRepository.findAll(spec);
    }

    @GetMapping("/count")
    public Map<String, Long> getCount(
            @RequestParam(required = false) Integer stationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTimeTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintPlanFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintPlanTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintRepairFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintRepairTo,
            @RequestParam(required = false) Integer repairCount,
            @RequestParam(required = false) Integer minTrips,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minDistance,
            @RequestParam(required = false) Integer maxDistance
    ) {
        Specification<Train> spec = createSpecification(stationId, arrivalTimeTo, maintPlanFrom, maintPlanTo, maintRepairFrom, maintRepairTo, repairCount, minTrips, minAge, maxAge, routeId, minDistance, maxDistance);
        return Map.of("count", trainRepository.count(spec));
    }

    private Specification<Train> createSpecification(Integer stationId, LocalDateTime arrivalTimeTo,
                                                     LocalDate maintPlanFrom, LocalDate maintPlanTo,
                                                     LocalDate maintRepairFrom, LocalDate maintRepairTo,
                                                     Integer repairCount, Integer minTrips,
                                                     Integer minAge, Integer maxAge,
                                                     Integer routeId, Integer minDistance, Integer maxDistance) {
        return Specification.where(TrainSpecification.hasStation(stationId))
                .and(TrainSpecification.arrivedAtStationBefore(stationId, arrivalTimeTo))
                .and(TrainSpecification.hadPlannedMaintenanceInDateRange(maintPlanFrom, maintPlanTo))
                .and(TrainSpecification.hasRepairInRange(maintRepairFrom, maintRepairTo))
                .and(TrainSpecification.hasRepairsCount(repairCount))
                .and(TrainSpecification.hasMinTrips(minTrips))
                .and(TrainSpecification.isAgeBetween(minAge, maxAge))
                .and(TrainSpecification.forRoute(routeId))
                .and(TrainSpecification.withRouteDistanceBetween(minDistance, maxDistance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getById(@PathVariable Integer id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + id + " не найден"));
        return ResponseEntity.ok(train);
    }

    @GetMapping("/{id}/personnel")
    public ResponseEntity<List<Employee>> getTrainPersonnel(@PathVariable Integer id) {
        List<Employee> personnel = trainPersonnelService.findPersonnelForTrain(id);
        return ResponseEntity.ok(personnel);
    }

    @PostMapping
    public Train create(@Valid @RequestBody TrainDto trainDto) {
        Train train = new Train();
        train.setModel(trainDto.getModel());
        train.setBuildDate(trainDto.getBuildDate());
        train.setLastMaintenanceDate(trainDto.getLastMaintenanceDate());
        train.setStatus(trainDto.getStatus());
        return trainRepository.save(train);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Train> update(@PathVariable Integer id, @Valid @RequestBody TrainDto trainDetails) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + id + " не найден"));
        train.setModel(trainDetails.getModel());
        train.setBuildDate(trainDetails.getBuildDate());
        train.setLastMaintenanceDate(trainDetails.getLastMaintenanceDate());
        train.setStatus(trainDetails.getStatus());
        final Train updatedTrain = trainRepository.save(train);
        return ResponseEntity.ok(updatedTrain);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + id + " не найден"));
        trainRepository.delete(train);
        return ResponseEntity.noContent().build();
    }
}