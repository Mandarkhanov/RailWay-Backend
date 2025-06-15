package com.mandarkhanov.controller;

import com.mandarkhanov.dto.SeatDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Car;
import com.mandarkhanov.model.Seat;
import com.mandarkhanov.repository.CarRepository;
import com.mandarkhanov.repository.SeatRepository;
import com.mandarkhanov.service.SeatSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CarRepository carRepository;

    @GetMapping("/filtered")
    public Iterable<Seat> getFiltered(
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) Boolean isAvailable
    ) {
        Specification<Seat> spec = Specification.where(SeatSpecification.hasAvailability(isAvailable))
                .and(SeatSpecification.forSchedule(scheduleId))
                .and(SeatSpecification.forRoute(routeId))
                .and(SeatSpecification.forDepartureDate(departureDate));
        return seatRepository.findAll(spec);
    }

    @GetMapping("/filtered/count")
    public Map<String, Long> getFilteredCount(
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) Integer routeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) Boolean isAvailable
    ) {
        Specification<Seat> spec = Specification.where(SeatSpecification.hasAvailability(isAvailable))
                .and(SeatSpecification.forSchedule(scheduleId))
                .and(SeatSpecification.forRoute(routeId))
                .and(SeatSpecification.forDepartureDate(departureDate));
        return Map.of("count", seatRepository.count(spec));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Seat> getById(@PathVariable Integer id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + id + " не найдено"));
        return ResponseEntity.ok(seat);
    }

    @PostMapping
    public Seat create(@Valid @RequestBody SeatDto seatDto) {
        Car car = carRepository.findById(seatDto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + seatDto.getCarId() + " не найдена"));
        Seat seat = new Seat();
        seat.setCar(car);
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setSeatType(seatDto.getSeatType());
        seat.setIsAvailable(seatDto.getIsAvailable());
        seat.setFeatures(seatDto.getFeatures());
        return seatRepository.save(seat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> update(@PathVariable Integer id, @Valid @RequestBody SeatDto seatDetails) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + id + " не найдено"));
        Car car = carRepository.findById(seatDetails.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + seatDetails.getCarId() + " не найдена"));

        seat.setCar(car);
        seat.setSeatNumber(seatDetails.getSeatNumber());
        seat.setSeatType(seatDetails.getSeatType());
        seat.setIsAvailable(seatDetails.getIsAvailable());
        seat.setFeatures(seatDetails.getFeatures());

        final Seat updatedSeat = seatRepository.save(seat);
        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Место с ID " + id + " не найдено"));
        seatRepository.delete(seat);
        return ResponseEntity.noContent().build();
    }
}