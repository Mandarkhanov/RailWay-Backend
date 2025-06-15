package com.mandarkhanov.repository;

import com.mandarkhanov.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer>, JpaSpecificationExecutor<Train> {
}