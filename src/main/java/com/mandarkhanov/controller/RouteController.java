package com.mandarkhanov.controller;

import com.mandarkhanov.model.Route;
import com.mandarkhanov.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {
    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public Iterable<Route> getAll() {
        return routeRepository.findAll();
    }
}
