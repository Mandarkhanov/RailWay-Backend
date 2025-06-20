package com.mandarkhanov.controller;

import com.mandarkhanov.dto.DepartmentDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Department;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.repository.DepartmentRepository;
import com.mandarkhanov.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Iterable<Department> getAll() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + id + " не найден"));

        return ResponseEntity.ok(department);
    }

    @GetMapping("/names")
    public List<String> getAllNames() {
        return departmentRepository.findAllNames();
    }

    @PostMapping
    public Department create(@Valid @RequestBody DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        if (departmentDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(departmentDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Сотрудник (менеджер) с ID " + departmentDto.getManagerId() + " не найден"));
            department.setManager(manager);
        }

        return departmentRepository.save(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + id + " не найден"));

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());

        if (departmentDetails.getManagerId() != null) {
            Employee manager = employeeRepository.findById(departmentDetails.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Сотрудник (менеджер) с ID " + departmentDetails.getManagerId() + " не найден"));
            department.setManager(manager);
        } else {
            department.setManager(null);
        }

        final Department updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отдел с ID " + id + " не найден"));

        departmentRepository.delete(department);

        return ResponseEntity.noContent().build();
    }
}
