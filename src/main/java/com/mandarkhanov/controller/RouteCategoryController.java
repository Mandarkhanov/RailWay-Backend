package com.mandarkhanov.controller;

import com.mandarkhanov.dto.RouteCategoryDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.RouteCategory;
import com.mandarkhanov.repository.RouteCategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route-categories")
public class RouteCategoryController {

    @Autowired
    private RouteCategoryRepository routeCategoryRepository;

    @GetMapping
    public Iterable<RouteCategory> getAll() {
        return routeCategoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteCategory> getById(@PathVariable Integer id) {
        RouteCategory category = routeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Категория маршрута с ID " + id + " не найдена"));
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public RouteCategory create(@Valid @RequestBody RouteCategoryDto categoryDto) {
        RouteCategory category = new RouteCategory();
        category.setName(categoryDto.getName());
        return routeCategoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteCategory> update(@PathVariable Integer id, @Valid @RequestBody RouteCategoryDto categoryDetails) {
        RouteCategory category = routeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Категория маршрута с ID " + id + " не найдена"));
        category.setName(categoryDetails.getName());
        final RouteCategory updatedCategory = routeCategoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        RouteCategory category = routeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Категория маршрута с ID " + id + " не найдена"));
        routeCategoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }
}