package com.mandarkhanov.service;

import com.mandarkhanov.dto.TicketDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.*;
import com.mandarkhanov.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final LuggageRepository luggageRepository;

    @Transactional
    public Ticket createTicket(TicketDto ticketDto) {
        Schedule schedule = scheduleRepository.findById(ticketDto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + ticketDto.getScheduleId() + " не найден"));
        Passenger passenger = passengerRepository.findById(ticketDto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + ticketDto.getPassengerId() + " не найден"));
        Seat seat = seatRepository.findById(ticketDto.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + ticketDto.getSeatId() + " не найдено"));

        if (!seat.getIsAvailable()) {
            throw new IllegalStateException("Место " + seat.getSeatNumber() + " в вагоне " + seat.getCar().getCarNumber() + " уже занято.");
        }

        seat.setIsAvailable(false);
        seatRepository.save(seat);

        Ticket ticket = new Ticket();
        ticket.setSchedule(schedule);
        ticket.setPassenger(passenger);
        ticket.setSeat(seat);
        ticket.setPrice(ticketDto.getPrice());
        ticket.setTicketStatus(ticketDto.getTicketStatus());

        if (ticketDto.getLuggageId() != null) {
            Luggage luggage = luggageRepository.findById(ticketDto.getLuggageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + ticketDto.getLuggageId() + " не найден"));
            ticket.setLuggage(luggage);
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicket(Integer ticketId, TicketDto ticketDto) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + ticketId + " не найден"));

        Schedule schedule = scheduleRepository.findById(ticketDto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + ticketDto.getScheduleId() + " не найден"));
        Passenger passenger = passengerRepository.findById(ticketDto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + ticketDto.getPassengerId() + " не найден"));
        Seat newSeat = seatRepository.findById(ticketDto.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + ticketDto.getSeatId() + " не найдено"));

        Seat oldSeat = ticket.getSeat();
        if (!oldSeat.getId().equals(newSeat.getId())) {
            if (!newSeat.getIsAvailable()) {
                throw new IllegalStateException("Новое место " + newSeat.getSeatNumber() + " уже занято.");
            }
            oldSeat.setIsAvailable(true);
            seatRepository.save(oldSeat);

            newSeat.setIsAvailable(false);
            seatRepository.save(newSeat);
        }

        ticket.setSchedule(schedule);
        ticket.setPassenger(passenger);
        ticket.setSeat(newSeat);
        ticket.setPrice(ticketDto.getPrice());
        ticket.setTicketStatus(ticketDto.getTicketStatus());

        if (ticketDto.getLuggageId() != null) {
            Luggage luggage = luggageRepository.findById(ticketDto.getLuggageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + ticketDto.getLuggageId() + " не найден"));
            ticket.setLuggage(luggage);
        } else {
            ticket.setLuggage(null);
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteTicket(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + ticketId + " не найден"));

        Seat seat = ticket.getSeat();
        if (seat != null) {
            seat.setIsAvailable(true);
            seatRepository.save(seat);
        }

        ticketRepository.delete(ticket);
    }
}