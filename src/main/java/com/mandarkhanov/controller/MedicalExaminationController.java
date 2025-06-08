package com.mandarkhanov.controller;

import com.mandarkhanov.dto.MedicalExaminationDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.MedicalExamination;
import com.mandarkhanov.repository.EmployeeRepository;
import com.mandarkhanov.repository.MedicalExaminationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-examinations")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicalExaminationController {

    @Autowired
    private MedicalExaminationRepository medicalExaminationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Iterable<MedicalExamination> getAll() {
        return medicalExaminationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalExamination> getById(@PathVariable Integer id) {
        MedicalExamination medicalExamination = medicalExaminationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медосмотр с ID " + id + " не найден"));
        return ResponseEntity.ok(medicalExamination);
    }

    @PostMapping
    public MedicalExamination create(@Valid @RequestBody MedicalExaminationDto medicalExaminationDto) {
        Employee employee = employeeRepository.findById(medicalExaminationDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + medicalExaminationDto.getEmployeeId() + " не найден"));

        MedicalExamination medicalExamination = new MedicalExamination();
        medicalExamination.setEmployee(employee);
        medicalExamination.setExaminationDate(medicalExaminationDto.getExaminationDate());
        medicalExamination.setResult(medicalExaminationDto.getResult());
        medicalExamination.setNotes(medicalExaminationDto.getNotes());

        return medicalExaminationRepository.save(medicalExamination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalExamination> update(@PathVariable Integer id, @Valid @RequestBody MedicalExaminationDto medicalExaminationDetails) {
        MedicalExamination medicalExamination = medicalExaminationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медосмотр с ID " + id + " не найден"));
        Employee employee = employeeRepository.findById(medicalExaminationDetails.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + medicalExaminationDetails.getEmployeeId() + " не найден"));

        medicalExamination.setEmployee(employee);
        medicalExamination.setExaminationDate(medicalExaminationDetails.getExaminationDate());
        medicalExamination.setResult(medicalExaminationDetails.getResult());
        medicalExamination.setNotes(medicalExaminationDetails.getNotes());

        final MedicalExamination updatedMedicalExamination = medicalExaminationRepository.save(medicalExamination);
        return ResponseEntity.ok(updatedMedicalExamination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        MedicalExamination medicalExamination = medicalExaminationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Медосмотр с ID " + id + " не найден"));

        medicalExaminationRepository.delete(medicalExamination);

        return ResponseEntity.noContent().build();
    }
}