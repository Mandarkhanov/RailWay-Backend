package com.example.dbproj.controller;

import com.example.dbproj.model.Position;
import com.example.dbproj.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("/positions")
    public Iterable<Position> getAll(){
        return positionRepository.findAll();
    }

    @GetMapping("/positions/names")
    public List<String> getAllNames() {
        return positionRepository.findAllNames();
    }

}
