package com.mandarkhanov.service;

import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.MedicalExamination;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalExaminationSpecification {

    public static Specification<MedicalExamination> hasPosition(Integer positionId) {
        return (root, query, criteriaBuilder) -> {
            if (positionId == null) return criteriaBuilder.conjunction();
            Join<MedicalExamination, Employee> employeeJoin = root.join("employee");
            return criteriaBuilder.equal(employeeJoin.get("position").get("id"), positionId);
        };
    }

    public static Specification<MedicalExamination> hasResult(Boolean result) {
        return (root, query, criteriaBuilder) -> {
            if (result == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("result"), result);
        };
    }

    public static Specification<MedicalExamination> inYear(Integer year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) return criteriaBuilder.conjunction();
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            return criteriaBuilder.between(root.get("examinationDate"), startDate, endDate);
        };
    }

    public static Specification<MedicalExamination> employeeHasGender(Character gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null) return criteriaBuilder.conjunction();
            Join<MedicalExamination, Employee> employeeJoin = root.join("employee");
            return criteriaBuilder.equal(employeeJoin.get("gender"), gender);
        };
    }

    public static Specification<MedicalExamination> employeeIsAgeBetween(Integer minAge, Integer maxAge) {
        return (root, query, criteriaBuilder) -> {
            Join<MedicalExamination, Employee> employeeJoin = root.join("employee");
            Specification<MedicalExamination> spec = Specification.where(null);
            if (minAge != null) {
                LocalDate maxBirthDate = LocalDate.now().minusYears(minAge);
                spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(employeeJoin.get("birthDate"), maxBirthDate));
            }
            if (maxAge != null) {
                LocalDate minBirthDate = LocalDate.now().minusYears(maxAge + 1).plusDays(1);
                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(employeeJoin.get("birthDate"), minBirthDate));
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }

    public static Specification<MedicalExamination> employeeHasSalaryBetween(BigDecimal minSalary, BigDecimal maxSalary) {
        return (root, query, criteriaBuilder) -> {
            Join<MedicalExamination, Employee> employeeJoin = root.join("employee");
            if (minSalary == null && maxSalary == null) return criteriaBuilder.conjunction();
            if (minSalary != null && maxSalary != null) {
                return criteriaBuilder.between(employeeJoin.get("salary"), minSalary, maxSalary);
            }
            if (minSalary != null) {
                return criteriaBuilder.greaterThanOrEqualTo(employeeJoin.get("salary"), minSalary);
            }
            return criteriaBuilder.lessThanOrEqualTo(employeeJoin.get("salary"), maxSalary);
        };
    }
}