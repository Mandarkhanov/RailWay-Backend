package com.mandarkhanov.repository;

import com.mandarkhanov.model.MedicalExamination;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalExaminationRepository extends PagingAndSortingRepository<MedicalExamination, Integer>, CrudRepository<MedicalExamination, Integer>, JpaSpecificationExecutor<MedicalExamination> {
}