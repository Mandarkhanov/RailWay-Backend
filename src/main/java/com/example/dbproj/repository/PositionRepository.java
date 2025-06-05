package com.example.dbproj.repository;

import com.example.dbproj.model.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, Integer>, CrudRepository<Position, Integer> {

    @Query("SELECT p.name FROM Position p")
    List<String> findAllNames();
}
