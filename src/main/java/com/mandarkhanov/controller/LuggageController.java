package com.mandarkhanov.controller;

import com.mandarkhanov.dto.LuggageDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Luggage;
import com.mandarkhanov.repository.LuggageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/luggage")
public class LuggageController {

    @Autowired
    private LuggageRepository luggageRepository;

    @GetMapping
    public Iterable<Luggage> getAll() {
        return luggageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Luggage> getById(@PathVariable Integer id) {
        Luggage luggage = luggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + id + " не найден"));
        return ResponseEntity.ok(luggage);
    }

    @PostMapping
    public Luggage create(@Valid @RequestBody LuggageDto luggageDto) {
        Luggage luggage = new Luggage();
        luggage.setWeightKg(luggageDto.getWeightKg());
        luggage.setPieces(luggageDto.getPieces());
        luggage.setStatus(luggageDto.getStatus());
        return luggageRepository.save(luggage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Luggage> update(@PathVariable Integer id, @Valid @RequestBody LuggageDto luggageDetails) {
        Luggage luggage = luggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + id + " не найден"));
        luggage.setWeightKg(luggageDetails.getWeightKg());
        luggage.setPieces(luggageDetails.getPieces());
        luggage.setStatus(luggageDetails.getStatus());
        final Luggage updatedLuggage = luggageRepository.save(luggage);
        return ResponseEntity.ok(updatedLuggage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Luggage luggage = luggageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + id + " не найден"));
        luggageRepository.delete(luggage);
        return ResponseEntity.noContent().build();
    }
}