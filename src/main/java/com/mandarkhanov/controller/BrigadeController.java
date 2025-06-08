package com.mandarkhanov.controller;

import com.mandarkhanov.dto.BrigadeDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Brigade;
import com.mandarkhanov.model.Department;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.repository.BrigadeRepository;
import com.mandarkhanov.repository.DepartmentRepository;
import com.mandarkhanov.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brigades")
@CrossOrigin(origins = "http://localhost:5173")
public class BrigadeController {
    @Autowired
    private BrigadeRepository brigadeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Iterable<Brigade> getAll() {
        return brigadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brigade> getById(@PathVariable Integer id) {
        Brigade brigade = brigadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Бригада с ID " + id + " не найдена"));
        return ResponseEntity.ok(brigade);
    }

    @GetMapping("/names")
    public List<String> getAllNames() {
        return brigadeRepository.findAllNames();
    }

    @PostMapping
    public Brigade create(@Valid @RequestBody BrigadeDto brigadeDto) {
        Brigade brigade = new Brigade();
        brigade.setName(brigadeDto.getName());

        if (brigadeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(brigadeDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + brigadeDto.getDepartmentId() + " не найден"));
            brigade.setDepartment(department);
        }

        if (brigadeDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(brigadeDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Сотрудник (менеджер) с ID " + brigadeDto.getManagerId() + " не найден"));
            brigade.setManager(manager);
        }

        return brigadeRepository.save(brigade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brigade> update(@PathVariable Integer id, @Valid @RequestBody BrigadeDto brigadeDetails) {
        Brigade brigade = brigadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Бригада с ID " + id + " не найдена"));

        brigade.setName(brigadeDetails.getName());

        if (brigadeDetails.getDepartmentId() != null) {
            Department department = departmentRepository.findById(brigadeDetails.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + brigadeDetails.getDepartmentId() + " не найден"));
            brigade.setDepartment(department);
        } else {
            brigade.setDepartment(null);
        }

        if (brigadeDetails.getManagerId() != null) {
            Employee manager = employeeRepository.findById(brigadeDetails.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Сотрудник (менеджер) с ID " + brigadeDetails.getManagerId() + " не найден"));
            brigade.setManager(manager);
        } else {
            brigade.setManager(null);
        }

        final Brigade updatedBrigade = brigadeRepository.save(brigade);
        return ResponseEntity.ok(updatedBrigade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Brigade brigade = brigadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Бригада с ID " + id + " не найдена"));

        brigadeRepository.delete(brigade);

        return ResponseEntity.noContent().build();
    }
}