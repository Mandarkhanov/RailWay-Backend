package com.mandarkhanov.controller;

import com.mandarkhanov.dto.TicketDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.*;
import com.mandarkhanov.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private LuggageRepository luggageRepository;

    @GetMapping
    public Iterable<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + id + " не найден"));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public Ticket create(@Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        updateTicketFromDto(ticket, ticketDto);
        return ticketRepository.save(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Integer id, @Valid @RequestBody TicketDto ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + id + " не найден"));
        updateTicketFromDto(ticket, ticketDetails);
        final Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + id + " не найден"));
        ticketRepository.delete(ticket);
        return ResponseEntity.noContent().build();
    }

    private void updateTicketFromDto(Ticket ticket, TicketDto dto) {
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + dto.getScheduleId() + " не найден"));
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + dto.getPassengerId() + " не найден"));
        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + dto.getSeatId() + " не найдено"));

        ticket.setSchedule(schedule);
        ticket.setPassenger(passenger);
        ticket.setSeat(seat);
        ticket.setPrice(dto.getPrice());
        ticket.setTicketStatus(dto.getTicketStatus());

        if (dto.getLuggageId() != null) {
            Luggage luggage = luggageRepository.findById(dto.getLuggageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Багаж с ID " + dto.getLuggageId() + " не найден"));
            ticket.setLuggage(luggage);
        } else {
            ticket.setLuggage(null);
        }
    }
}