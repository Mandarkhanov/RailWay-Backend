package com.mandarkhanov.controller;

import com.mandarkhanov.dto.TrainTypeDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.TrainType;
import com.mandarkhanov.repository.TrainTypeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train-types")
public class TrainTypeController {

    @Autowired
    private TrainTypeRepository trainTypeRepository;

    @GetMapping
    public Iterable<TrainType> getAll() {
        return trainTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainType> getById(@PathVariable Integer id) {
        TrainType trainType = trainTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип поезда с ID " + id + " не найден"));
        return ResponseEntity.ok(trainType);
    }

    @PostMapping
    public TrainType create(@Valid @RequestBody TrainTypeDto trainTypeDto) {
        TrainType trainType = new TrainType();
        trainType.setName(trainTypeDto.getName());
        return trainTypeRepository.save(trainType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainType> update(@PathVariable Integer id, @Valid @RequestBody TrainTypeDto trainTypeDetails) {
        TrainType trainType = trainTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип поезда с ID " + id + " не найден"));
        trainType.setName(trainTypeDetails.getName());
        final TrainType updatedTrainType = trainTypeRepository.save(trainType);
        return ResponseEntity.ok(updatedTrainType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        TrainType trainType = trainTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип поезда с ID " + id + " не найден"));
        trainTypeRepository.delete(trainType);
        return ResponseEntity.noContent().build();
    }
}