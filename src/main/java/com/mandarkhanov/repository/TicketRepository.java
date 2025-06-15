package com.mandarkhanov.repository;

import com.mandarkhanov.model.Ticket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Integer>, CrudRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

    /**
     * Находит все билеты, принадлежащие списку пассажиров.
     * @param passengerIds список ID пассажиров.
     * @return список билетов.
     */
    List<Ticket> findByPassengerIdIn(List<Integer> passengerIds);
}