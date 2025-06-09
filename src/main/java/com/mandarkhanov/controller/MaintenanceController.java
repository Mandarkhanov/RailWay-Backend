package com.mandarkhanov.controller;

import com.mandarkhanov.dto.MaintenanceDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Brigade;
import com.mandarkhanov.model.Maintenance;
import com.mandarkhanov.model.Train;
import com.mandarkhanov.repository.BrigadeRepository;
import com.mandarkhanov.repository.MaintenanceRepository;
import com.mandarkhanov.repository.TrainRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenances")
@CrossOrigin(origins = "http://localhost:5173")
public class MaintenanceController {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private BrigadeRepository brigadeRepository;

    @GetMapping
    public Iterable<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getById(@PathVariable Integer id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о ТО с ID " + id + " не найдена"));
        return ResponseEntity.ok(maintenance);
    }

    @PostMapping
    public Maintenance create(@Valid @RequestBody MaintenanceDto maintenanceDto) {
        Train train = trainRepository.findById(maintenanceDto.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + maintenanceDto.getTrainId() + " не найден"));

        Maintenance maintenance = new Maintenance();
        maintenance.setTrain(train);
        maintenance.setStartDate(maintenanceDto.getStartDate());
        maintenance.setEndDate(maintenanceDto.getEndDate());
        maintenance.setType(maintenanceDto.getType());
        maintenance.setResult(maintenanceDto.getResult());
        maintenance.setIsRepair(maintenanceDto.getIsRepair());

        if (maintenanceDto.getBrigadeId() != null) {
            Brigade brigade = brigadeRepository.findById(maintenanceDto.getBrigadeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Бригада с ID " + maintenanceDto.getBrigadeId() + " не найдена"));
            maintenance.setBrigade(brigade);
        }

        return maintenanceRepository.save(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> update(@PathVariable Integer id, @Valid @RequestBody MaintenanceDto maintenanceDetails) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о ТО с ID " + id + " не найдена"));
        Train train = trainRepository.findById(maintenanceDetails.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + maintenanceDetails.getTrainId() + " не найден"));

        maintenance.setTrain(train);
        maintenance.setStartDate(maintenanceDetails.getStartDate());
        maintenance.setEndDate(maintenanceDetails.getEndDate());
        maintenance.setType(maintenanceDetails.getType());
        maintenance.setResult(maintenanceDetails.getResult());
        maintenance.setIsRepair(maintenanceDetails.getIsRepair());

        if (maintenanceDetails.getBrigadeId() != null) {
            Brigade brigade = brigadeRepository.findById(maintenanceDetails.getBrigadeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Бригада с ID " + maintenanceDetails.getBrigadeId() + " не найдена"));
            maintenance.setBrigade(brigade);
        } else {
            maintenance.setBrigade(null);
        }

        final Maintenance updatedMaintenance = maintenanceRepository.save(maintenance);
        return ResponseEntity.ok(updatedMaintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о ТО с ID " + id + " не найдена"));
        maintenanceRepository.delete(maintenance);
        return ResponseEntity.noContent().build();
    }
}