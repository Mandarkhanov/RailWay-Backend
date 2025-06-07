package com.mandarkhanov.controller;

import com.mandarkhanov.model.RouteStop;
import com.mandarkhanov.repository.RouteStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/route-stops")
@CrossOrigin(origins = "http://localhost:5173")
public class RouteStopController {
    @Autowired
    private RouteStopRepository routeStopRepository;

    @GetMapping
    public Iterable<RouteStop> getAll() {
        return routeStopRepository.findAll();
    }
}