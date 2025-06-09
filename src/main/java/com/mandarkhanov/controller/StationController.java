package com.mandarkhanov.controller;

import com.mandarkhanov.dto.StationDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Station;
import com.mandarkhanov.repository.StationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stations")
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @GetMapping
    public Iterable<Station> getAll() {
        return stationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getById(@PathVariable Integer id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Станция с ID " + id + " не найдена"));
        return ResponseEntity.ok(station);
    }

    @PostMapping
    public Station create(@Valid @RequestBody StationDto stationDto) {
        Station station = new Station();
        station.setName(stationDto.getName());
        station.setAddress(stationDto.getAddress());
        station.setRegion(stationDto.getRegion());
        return stationRepository.save(station);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> update(@PathVariable Integer id, @Valid @RequestBody StationDto stationDetails) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Станция с ID " + id + " не найдена"));
        station.setName(stationDetails.getName());
        station.setAddress(stationDetails.getAddress());
        station.setRegion(stationDetails.getRegion());
        final Station updatedStation = stationRepository.save(station);
        return ResponseEntity.ok(updatedStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Станция с ID " + id + " не найдена"));
        stationRepository.delete(station);
        return ResponseEntity.noContent().build();
    }
}