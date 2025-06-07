package com.mandarkhanov.controller;


import com.mandarkhanov.model.Train;
import com.mandarkhanov.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trains")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainController {
    @Autowired
    private TrainRepository trainRepository;

    @GetMapping
    public Iterable<Train> getAll() {
        return trainRepository.findAll();
    }
}
