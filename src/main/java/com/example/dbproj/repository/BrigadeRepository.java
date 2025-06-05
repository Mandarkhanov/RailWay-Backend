package com.example.dbproj.repository;

import com.example.dbproj.model.Brigade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrigadeRepository extends PagingAndSortingRepository<Brigade, Integer>, CrudRepository<Brigade, Integer> {
    /**
     * Retrieves only the names of all brigades.
     *
     * @return a List of Strings, each representing a brigade name.
     */
    @Query("SELECT b.name FROM Brigade b")
    List<String> findAllNames();
}
