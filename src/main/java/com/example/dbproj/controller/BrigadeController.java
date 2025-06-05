package com.example.dbproj.controller;

import com.example.dbproj.model.Brigade;
import com.example.dbproj.repository.BrigadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brigades")
@CrossOrigin(origins = "http://localhost:5173")
public class BrigadeController {
    @Autowired
    private BrigadeRepository brigadeRepository;

    @GetMapping
    public Iterable<Brigade> getAll() {
        return brigadeRepository.findAll();
    }

    /**
     * Handles GET requests to /brigades/names.
     * Returns a list containing only the names of all brigades.
     *
     * @return a List of Strings representing brigade names.
     */
    @GetMapping("/names")
    public List<String> getAllNames() {
        return brigadeRepository.findAllNames();
    }
}
