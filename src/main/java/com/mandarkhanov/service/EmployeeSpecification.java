package com.mandarkhanov.service;

import com.mandarkhanov.model.Department;
import com.mandarkhanov.model.Employee;
import com.mandarkhanov.model.EmployeeBrigade;
import com.mandarkhanov.model.Position;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeSpecification {

    public static Specification<Employee> hasDepartment(Integer departmentId) {
        return (root, query, criteriaBuilder) -> {
            if (departmentId == null) return criteriaBuilder.conjunction();
            Join<Employee, Position> positionJoin = root.join("position");
            return criteriaBuilder.equal(positionJoin.get("department").get("id"), departmentId);
        };
    }

    public static Specification<Employee> hasBrigade(Integer brigadeId) {
        return (root, query, criteriaBuilder) -> {
            if (brigadeId == null) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);
            Join<Employee, EmployeeBrigade> employeeBrigadeJoin = root.join("employeeBrigades");
            return criteriaBuilder.equal(employeeBrigadeJoin.get("brigade").get("id"), brigadeId);
        };
    }

    public static Specification<Employee> hasExperienceGreaterThan(Integer years) {
        return (root, query, criteriaBuilder) -> {
            if (years == null) return criteriaBuilder.conjunction();
            LocalDate date = LocalDate.now().minusYears(years);
            return criteriaBuilder.lessThanOrEqualTo(root.get("hireDate"), date);
        };
    }

    public static Specification<Employee> hasGender(Character gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("gender"), gender);
        };
    }

    public static Specification<Employee> isAgeBetween(Integer minAge, Integer maxAge) {
        return (root, query, criteriaBuilder) -> {
            Specification<Employee> spec = Specification.where(null);
            if (minAge != null) {
                LocalDate maxBirthDate = LocalDate.now().minusYears(minAge);
                spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("birthDate"), maxBirthDate));
            }
            if (maxAge != null) {
                LocalDate minBirthDate = LocalDate.now().minusYears(maxAge + 1).plusDays(1);
                spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("birthDate"), minBirthDate));
            }
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }

    public static Specification<Employee> hasChildren(Boolean hasChildren) {
        return (root, query, criteriaBuilder) -> {
            if (hasChildren == null) return criteriaBuilder.conjunction();
            if (hasChildren) {
                return criteriaBuilder.greaterThan(root.get("childrenCount"), 0);
            } else {
                return criteriaBuilder.equal(root.get("childrenCount"), 0);
            }
        };
    }

    public static Specification<Employee> isSalaryBetween(BigDecimal minSalary, BigDecimal maxSalary) {
        return (root, query, criteriaBuilder) -> {
            if (minSalary == null && maxSalary == null) return criteriaBuilder.conjunction();
            if (minSalary != null && maxSalary != null) {
                return criteriaBuilder.between(root.get("salary"), minSalary, maxSalary);
            }
            if (minSalary != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("salary"), maxSalary);
        };
    }

    public static Specification<Employee> isManager(Boolean isManager) {
        return (root, query, criteriaBuilder) -> {
            if (isManager == null || !isManager) {
                return criteriaBuilder.conjunction();
            }
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Department> departmentRoot = subquery.from(Department.class);
            subquery.select(departmentRoot.get("manager").get("id"));
            subquery.where(criteriaBuilder.isNotNull(departmentRoot.get("manager")));

            return root.get("id").in(subquery);
        };
    }
}