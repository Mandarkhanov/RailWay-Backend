package com.example.dbproj.repository;

import com.example.dbproj.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer>, CrudRepository<Department, Integer> {

    /**
     * Retrieves only the names of all departments.
     * Uses a JPQL query to select only the 'name' field.
     *
     * @return a List of Strings, each representing a department name.
     */
    @Query("SELECT d.name FROM Department d")
    List<String> findAllNames(); // Новый метод для получения только названий

}
