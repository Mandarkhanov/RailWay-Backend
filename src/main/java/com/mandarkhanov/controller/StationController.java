package com.mandarkhanov.controller;


import com.mandarkhanov.model.Station;
import com.mandarkhanov.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stations")
@CrossOrigin(origins = "http://localhost:5173")
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @GetMapping
    public Iterable<Station> getAll() {
        return stationRepository.findAll();
    }
}
