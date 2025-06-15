package com.mandarkhanov.controller;

import com.mandarkhanov.dto.TicketDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Ticket;
import com.mandarkhanov.repository.TicketRepository;
import com.mandarkhanov.service.TicketService;
import com.mandarkhanov.service.TicketSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public Iterable<Ticket> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDateTo,
            @RequestParam(required = false) String ticketStatus,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minDistance,
            @RequestParam(required = false) Integer maxDistance,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasPurchaseDateBetween(purchaseDateFrom, purchaseDateTo))
                .and(TicketSpecification.hasStatus(ticketStatus))
                .and(TicketSpecification.forRoute(routeId))
                .and(TicketSpecification.withRouteDistanceBetween(minDistance, maxDistance))
                .and(TicketSpecification.hasPriceBetween(minPrice, maxPrice));
        return ticketRepository.findAll(spec);
    }

    @GetMapping("/count")
    public Map<String, Long> getCount(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDateTo,
            @RequestParam(required = false) String ticketStatus,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) Integer minDistance,
            @RequestParam(required = false) Integer maxDistance,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasPurchaseDateBetween(purchaseDateFrom, purchaseDateTo))
                .and(TicketSpecification.hasStatus(ticketStatus))
                .and(TicketSpecification.forRoute(routeId))
                .and(TicketSpecification.withRouteDistanceBetween(minDistance, maxDistance))
                .and(TicketSpecification.hasPriceBetween(minPrice, maxPrice));
        return Map.of("count", ticketRepository.count(spec));
    }

    @GetMapping("/returned/count")
    public Map<String, Long> getReturnedTicketsCount(
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasStatus("возвращен"))
                .and(TicketSpecification.forSchedule(scheduleId))
                .and(TicketSpecification.forRoute(routeId))
                .and(TicketSpecification.forDepartureDate(departureDate));

        long count = ticketRepository.count(spec);
        return Map.of("count", count);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Билет с ID " + id + " не найден"));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public Ticket create(@Valid @RequestBody TicketDto ticketDto) {
        return ticketService.createTicket(ticketDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Integer id, @Valid @RequestBody TicketDto ticketDetails) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}