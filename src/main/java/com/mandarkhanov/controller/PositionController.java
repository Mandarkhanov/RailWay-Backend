package com.mandarkhanov.controller;

import com.mandarkhanov.dto.PositionDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Department;
import com.mandarkhanov.model.Position;
import com.mandarkhanov.repository.DepartmentRepository;
import com.mandarkhanov.repository.PositionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping()
    public Iterable<Position> getAll(){
        return positionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getById(@PathVariable Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Должность с ID " + id + " не найдена"));

        return ResponseEntity.ok(position);
    }

    @GetMapping("/names")
    public List<String> getAllNames() {
        return positionRepository.findAllNames();
    }

    @PostMapping
    public Position create(@Valid @RequestBody PositionDto positionDto) {
        Department department = departmentRepository.findById(positionDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + positionDto.getDepartmentId() + " не найден"));

        Position position = new Position();
        position.setName(positionDto.getName());
        position.setDepartment(department);
        position.setMinSalary(positionDto.getMinSalary());
        position.setMaxSalary(positionDto.getMaxSalary());
        position.setDescription(positionDto.getDescription());

        return positionRepository.save(position);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> update(@PathVariable Integer id,@Valid @RequestBody PositionDto positionDetails) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Должность с ID " + id + " не найдена"));
        Department department = departmentRepository.findById(positionDetails.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + positionDetails.getDepartmentId() + " не найден"));

        position.setName(positionDetails.getName());
        position.setDepartment(department);
        position.setMinSalary(positionDetails.getMinSalary());
        position.setMaxSalary(positionDetails.getMaxSalary());
        position.setDescription(positionDetails.getDescription());

        final Position updatedPosition = positionRepository.save(position);
        return ResponseEntity.ok(updatedPosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Должность с ID " + id + " не найдена"));

        positionRepository.delete(position);

        return ResponseEntity.noContent().build();
    }
}
