package com.mandarkhanov.service;

import com.mandarkhanov.model.Passenger;
import com.mandarkhanov.model.Ticket;
import com.mandarkhanov.model.User;
import com.mandarkhanov.model.UserPassenger;
import com.mandarkhanov.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TicketRepository ticketRepository;

    public List<Ticket> findTicketsForUser(User user) {
        List<Integer> passengerIds = user.getUserPassengers().stream()
                .map(UserPassenger::getPassenger)
                .map(Passenger::getId)
                .collect(Collectors.toList());

        if (passengerIds.isEmpty()) {
            return Collections.emptyList();
        }

        return ticketRepository.findByPassengerIdIn(passengerIds);
    }
}