package com.example.dbproj.controller;

import com.example.dbproj.model.MedicalExamination;
import com.example.dbproj.repository.MedicalExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-examinations")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicalExaminationController {

    @Autowired
    private MedicalExaminationRepository medicalExaminationRepository;

    @GetMapping
    public Iterable<MedicalExamination> getAll() {
        return medicalExaminationRepository.findAll();
    }
}