package com.mandarkhanov.controller;

import com.mandarkhanov.dto.RouteDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Route;
import com.mandarkhanov.model.RouteCategory;
import com.mandarkhanov.model.Station;
import com.mandarkhanov.repository.RouteCategoryRepository;
import com.mandarkhanov.repository.RouteRepository;
import com.mandarkhanov.repository.StationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RouteCategoryRepository routeCategoryRepository;

    @GetMapping
    public Iterable<Route> getAll() {
        return routeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getById(@PathVariable Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + id + " не найден"));
        return ResponseEntity.ok(route);
    }

    @PostMapping
    public Route create(@Valid @RequestBody RouteDto routeDto) {
        Station startStation = stationRepository.findById(routeDto.getStartStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Начальная станция с ID " + routeDto.getStartStationId() + " не найдена"));
        Station endStation = stationRepository.findById(routeDto.getEndStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Конечная станция с ID " + routeDto.getEndStationId() + " не найдена"));

        Route route = new Route();
        route.setName(routeDto.getName());
        route.setStartStation(startStation);
        route.setEndStation(endStation);
        route.setDistanceKm(routeDto.getDistanceKm());

        if (routeDto.getCategoryId() != null) {
            RouteCategory category = routeCategoryRepository.findById(routeDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Категория маршрута с ID " + routeDto.getCategoryId() + " не найдена"));
            route.setCategory(category);
        }

        return routeRepository.save(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@PathVariable Integer id, @Valid @RequestBody RouteDto routeDetails) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + id + " не найден"));
        Station startStation = stationRepository.findById(routeDetails.getStartStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Начальная станция с ID " + routeDetails.getStartStationId() + " не найдена"));
        Station endStation = stationRepository.findById(routeDetails.getEndStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Конечная станция с ID " + routeDetails.getEndStationId() + " не найдена"));

        route.setName(routeDetails.getName());
        route.setStartStation(startStation);
        route.setEndStation(endStation);
        route.setDistanceKm(routeDetails.getDistanceKm());

        if (routeDetails.getCategoryId() != null) {
            RouteCategory category = routeCategoryRepository.findById(routeDetails.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Категория маршрута с ID " + routeDetails.getCategoryId() + " не найдена"));
            route.setCategory(category);
        } else {
            route.setCategory(null);
        }

        final Route updatedRoute = routeRepository.save(route);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + id + " не найден"));
        routeRepository.delete(route);
        return ResponseEntity.noContent().build();
    }
}