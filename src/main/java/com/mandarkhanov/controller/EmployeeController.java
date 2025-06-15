package com.mandarkhanov.controller;

import com.mandarkhanov.dto.EmployeeDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.Position;
import com.mandarkhanov.repository.EmployeeRepository;
import com.mandarkhanov.repository.PositionRepository;
import com.mandarkhanov.service.EmployeeSpecification;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping
    public Iterable<Employee> getAll(
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) Integer brigadeId,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Character gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Boolean hasChildren,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(required = false) Boolean isManager) {

        Specification<Employee> spec = createSpecification(departmentId, brigadeId, minExperience, gender, minAge, maxAge, hasChildren, minSalary, maxSalary, isManager);
        return employeeRepository.findAll(spec);
    }

    @GetMapping("/count")
    public Map<String, Long> getCount(
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) Integer brigadeId,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Character gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Boolean hasChildren,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(required = false) Boolean isManager) {

        Specification<Employee> spec = createSpecification(departmentId, brigadeId, minExperience, gender, minAge, maxAge, hasChildren, minSalary, maxSalary, isManager);
        long count = employeeRepository.count(spec);
        return Map.of("count", count);
    }

    private Specification<Employee> createSpecification(
            Integer departmentId, Integer brigadeId, Integer minExperience, Character gender,
            Integer minAge, Integer maxAge, Boolean hasChildren,
            BigDecimal minSalary, BigDecimal maxSalary, Boolean isManager) {

        return Specification.where(EmployeeSpecification.hasDepartment(departmentId))
                .and(EmployeeSpecification.hasBrigade(brigadeId))
                .and(EmployeeSpecification.hasExperienceGreaterThan(minExperience))
                .and(EmployeeSpecification.hasGender(gender))
                .and(EmployeeSpecification.isAgeBetween(minAge, maxAge))
                .and(EmployeeSpecification.hasChildren(hasChildren))
                .and(EmployeeSpecification.isSalaryBetween(minSalary, maxSalary))
                .and(EmployeeSpecification.isManager(isManager));
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