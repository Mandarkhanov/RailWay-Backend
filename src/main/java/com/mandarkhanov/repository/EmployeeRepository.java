package com.mandarkhanov.repository;

import com.mandarkhanov.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT e.firstName || ' ' || e.lastName FROM Employee e")
    List<String> findAllFullNames();

    /**
     * Finds all employees by their IDs and eagerly fetches their position and department.
     * @param ids collection of employee IDs
     * @return a list of employees with details
     */
    @EntityGraph(attributePaths = {"position", "position.department"})
    List<Employee> findWithDetailsByIdIn(Collection<Integer> ids);
}