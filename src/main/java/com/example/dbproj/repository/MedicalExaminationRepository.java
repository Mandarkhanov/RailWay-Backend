package com.example.dbproj.repository;

import com.example.dbproj.model.MedicalExamination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalExaminationRepository extends PagingAndSortingRepository<MedicalExamination, Integer>, CrudRepository<MedicalExamination, Integer> {
}
