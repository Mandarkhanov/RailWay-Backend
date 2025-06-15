package com.mandarkhanov.service;

import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Brigade;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.EmployeeBrigade;
import com.mandarkhanov.model.Maintenance;
import com.mandarkhanov.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainPersonnelService {
    private final MaintenanceRepository maintenanceRepository;
    private final EmployeeBrigadeRepository employeeBrigadeRepository;
    private final BrigadeRepository brigadeRepository;
    private final EmployeeRepository employeeRepository;
    private final TrainRepository trainRepository;

    @Transactional(readOnly = true)
    public List<Employee> findPersonnelForTrain(Integer trainId) {
        trainRepository.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + trainId + " не найден"));

        List<Maintenance> maintenances = maintenanceRepository.findByTrainId(trainId);
        if (maintenances.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Integer> brigadeIds = maintenances.stream()
                .map(Maintenance::getBrigade)
                .filter(Objects::nonNull)
                .map(Brigade::getId)
                .collect(Collectors.toSet());

        if (brigadeIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<EmployeeBrigade> employeeBrigades = employeeBrigadeRepository.findByBrigadeIdIn(brigadeIds);
        Set<Integer> employeeIds = employeeBrigades.stream()
                .map(EmployeeBrigade::getEmployee)
                .filter(Objects::nonNull)
                .map(Employee::getId)
                .collect(Collectors.toSet());

        List<Brigade> brigades = brigadeRepository.findAllById(brigadeIds);
        brigades.stream()
                .map(Brigade::getManager)
                .filter(Objects::nonNull)
                .map(Employee::getId)
                .forEach(employeeIds::add);

        if (employeeIds.isEmpty()) {
            return Collections.emptyList();
        }

        return employeeRepository.findWithDetailsByIdIn(employeeIds);
    }
}