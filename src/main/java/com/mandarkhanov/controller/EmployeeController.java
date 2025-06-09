package com.mandarkhanov.controller;

import com.mandarkhanov.dto.EmployeeDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.Position;
import com.mandarkhanov.repository.EmployeeRepository;
import com.mandarkhanov.repository.PositionRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/names")
    public List<String> getAllFullNames() {
        return employeeRepository.findAllFullNames();
    }

    @PostMapping
    public Employee create(@Valid @RequestBody EmployeeDto employeeDto) {
        Position position = positionRepository.findById(employeeDto.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Должность с ID " + employeeDto.getPositionId() + " не найдена"));

        validateSalary(employeeDto.getSalary(), position);

        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setBirthDate(employeeDto.getBirthDate());
        if (employeeDto.getGender() != null && !employeeDto.getGender().isEmpty()) {
            employee.setGender(employeeDto.getGender().charAt(0));
        }
        employee.setHireDate(employeeDto.getHireDate());
        employee.setPosition(position);
        employee.setSalary(employeeDto.getSalary());
        employee.setChildrenCount(employeeDto.getChildrenCount());
        employee.setIsActive(employeeDto.getIsActive());

        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @Valid @RequestBody EmployeeDto employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));
        Position position = positionRepository.findById(employeeDetails.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Должность с ID " + employeeDetails.getPositionId() + " не найдена"));

        validateSalary(employeeDetails.getSalary(), position);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setBirthDate(employeeDetails.getBirthDate());
        if (employeeDetails.getGender() != null && !employeeDetails.getGender().isEmpty()) {
            employee.setGender(employeeDetails.getGender().charAt(0));
        } else {
            employee.setGender(null);
        }
        employee.setHireDate(employeeDetails.getHireDate());
        employee.setPosition(position);
        employee.setSalary(employeeDetails.getSalary());
        employee.setChildrenCount(employeeDetails.getChildrenCount());
        employee.setIsActive(employeeDetails.getIsActive());

        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));

        employeeRepository.delete(employee);

        return ResponseEntity.noContent().build();
    }

    /**
     * Приватный метод для валидации зарплаты сотрудника
     * в соответствии с диапазоном в его должности.
     */
    private void validateSalary(BigDecimal salary, Position position) {
        if (salary == null) {
            return;
        }

        BigDecimal minSalary = position.getMinSalary();
        BigDecimal maxSalary = position.getMaxSalary();

        if (minSalary != null && salary.compareTo(minSalary) < 0) {
            throw new ValidationException("Зарплата " + salary + " не может быть меньше минимальной (" + minSalary + ") для данной должности.");
        }
        if (maxSalary != null && salary.compareTo(maxSalary) > 0) {
            throw new ValidationException("Зарплата " + salary + " не может быть больше максимальной (" + maxSalary + ") для данной должности.");
        }
    }
}