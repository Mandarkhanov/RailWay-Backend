package com.example.dbproj.controller;

import com.example.dbproj.model.Department;
import com.example.dbproj.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Or your Vite frontend port, or "*" for dev
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    public Iterable<Department> getAll() {
        return departmentRepository.findAll();
    }

    /**
     * Handles GET requests to /departments/names.
     * Returns a list containing only the names of all departments.
     *
     * @return a List of Strings representing department names.
     */
    @GetMapping("/departments/names")
    public List<String> getAllNames() {
        return departmentRepository.findAllNames();
    }
}
