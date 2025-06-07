package com.mandarkhanov.controller;

import com.mandarkhanov.model.Employee;
import com.mandarkhanov.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    /**
     * Handles GET requests to /employees/names.
     * Returns a list containing only the full names of all employees.
     *
     * @return a List of Strings representing employee full names.
     */
    @GetMapping("/names")
    public List<String> getAllFullNames() {
        return employeeRepository.findAllFullNames();
    }


}
