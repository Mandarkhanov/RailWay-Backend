package com.mandarkhanov.repository;

import com.mandarkhanov.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer>, CrudRepository<Employee, Integer> {

    /**
     * Retrieves a list of full names (firstName + lastName) of all employees.
     *
     * @return a List of Strings representing employee full names.
     */
    @Query("SELECT e.firstName || ' ' || e.lastName FROM Employee e")
    List<String> findAllFullNames();
}
