package com.mandarkhanov.repository;

import com.mandarkhanov.model.Brigade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrigadeRepository extends JpaRepository<Brigade, Integer> {

    @Query("SELECT b.name FROM Brigade b")
    List<String> findAllNames();
}