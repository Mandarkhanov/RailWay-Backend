package com.mandarkhanov.repository;

import com.mandarkhanov.model.EmployeeBrigade;
import com.mandarkhanov.model.EmployeeBrigadeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeBrigadeRepository extends JpaRepository<EmployeeBrigade, EmployeeBrigadeId> {

    List<EmployeeBrigade> findByBrigadeIdIn(Collection<Integer> brigadeIds);
}