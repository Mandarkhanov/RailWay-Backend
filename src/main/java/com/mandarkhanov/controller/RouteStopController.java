package com.mandarkhanov.controller;

import com.mandarkhanov.dto.RouteStopDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Route;
import com.mandarkhanov.model.RouteStop;
import com.mandarkhanov.model.Station;
import com.mandarkhanov.repository.RouteRepository;
import com.mandarkhanov.repository.RouteStopRepository;
import com.mandarkhanov.repository.StationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/route-stops")
@CrossOrigin(origins = "http://localhost:5173")
public class RouteStopController {
    @Autowired
    private RouteStopRepository routeStopRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StationRepository stationRepository;

    @GetMapping
    public Iterable<RouteStop> getAll() {
        return routeStopRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteStop> getById(@PathVariable Integer id) {
        RouteStop routeStop = routeStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Остановка на маршруте с ID " + id + " не найдена"));
        return ResponseEntity.ok(routeStop);
    }

    @PostMapping
    public RouteStop create(@Valid @RequestBody RouteStopDto routeStopDto) {
        Route route = routeRepository.findById(routeStopDto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + routeStopDto.getRouteId() + " не найден"));
        Station station = stationRepository.findById(routeStopDto.getStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Станция с ID " + routeStopDto.getStationId() + " не найдена"));

        RouteStop routeStop = new RouteStop();
        routeStop.setRoute(route);
        routeStop.setStation(station);
        routeStop.setStopOrder(routeStopDto.getStopOrder());
        routeStop.setArrivalOffset(routeStopDto.getArrivalOffset());
        routeStop.setDepartureOffset(routeStopDto.getDepartureOffset());
        routeStop.setPlatform(routeStopDto.getPlatform());

        return routeStopRepository.save(routeStop);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteStop> update(@PathVariable Integer id, @Valid @RequestBody RouteStopDto routeStopDetails) {
        RouteStop routeStop = routeStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Остановка на маршруте с ID " + id + " не найдена"));
        Route route = routeRepository.findById(routeStopDetails.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Маршрут с ID " + routeStopDetails.getRouteId() + " не найден"));
        Station station = stationRepository.findById(routeStopDetails.getStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Станция с ID " + routeStopDetails.getStationId() + " не найдена"));

        routeStop.setRoute(route);
        routeStop.setStation(station);
        routeStop.setStopOrder(routeStopDetails.getStopOrder());
        routeStop.setArrivalOffset(routeStopDetails.getArrivalOffset());
        routeStop.setDepartureOffset(routeStopDetails.getDepartureOffset());
        routeStop.setPlatform(routeStopDetails.getPlatform());

        final RouteStop updatedRouteStop = routeStopRepository.save(routeStop);
        return ResponseEntity.ok(updatedRouteStop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        RouteStop routeStop = routeStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Остановка на маршруте с ID " + id + " не найдена"));
        routeStopRepository.delete(routeStop);
        return ResponseEntity.noContent().build();
    }
}