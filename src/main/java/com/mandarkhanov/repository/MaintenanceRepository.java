package com.mandarkhanov.repository;

import com.mandarkhanov.model.Maintenance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends PagingAndSortingRepository<Maintenance, Integer>, CrudRepository<Maintenance, Integer>, JpaSpecificationExecutor<Maintenance> {

    List<Maintenance> findByTrainId(Integer trainId);
}