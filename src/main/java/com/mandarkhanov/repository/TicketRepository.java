package com.mandarkhanov.repository;

import com.mandarkhanov.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Integer>, CrudRepository<Ticket, Integer> {
}