package com.mandarkhanov.controller;

import com.mandarkhanov.dto.TrainDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Train;
import com.mandarkhanov.repository.TrainRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trains")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainController {
    @Autowired
    private TrainRepository trainRepository;

    @GetMapping
    public Iterable<Train> getAll() {
        return trainRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getById(@PathVariable Integer id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + id + " не найден"));
        return ResponseEntity.ok(train);
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