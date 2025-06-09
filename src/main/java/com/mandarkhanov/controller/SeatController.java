package com.mandarkhanov.controller;

import com.mandarkhanov.dto.SeatDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Car;
import com.mandarkhanov.model.Seat;
import com.mandarkhanov.repository.CarRepository;
import com.mandarkhanov.repository.SeatRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CarRepository carRepository;

    @GetMapping
    public Iterable<Seat> getAll() {
        return seatRepository.findAll();
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
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + seatDto.getCarId() + " не найден"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + seatDetails.getCarId() + " не найден"));

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