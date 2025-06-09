package com.mandarkhanov.controller;

import com.mandarkhanov.dto.PassengerDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Passenger;
import com.mandarkhanov.repository.PassengerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public Iterable<Passenger> getAll() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getById(@PathVariable Integer id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + id + " не найден"));
        return ResponseEntity.ok(passenger);
    }

    @PostMapping
    public Passenger create(@Valid @RequestBody PassengerDto passengerDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setMiddleName(passengerDto.getMiddleName());
        passenger.setBirthDate(passengerDto.getBirthDate());
        if (passengerDto.getGender() != null && !passengerDto.getGender().isEmpty()) {
            passenger.setGender(passengerDto.getGender().charAt(0));
        }
        passenger.setPassportSeries(passengerDto.getPassportSeries());
        passenger.setPassportNumber(passengerDto.getPassportNumber());
        passenger.setPhoneNumber(passengerDto.getPhoneNumber());
        passenger.setEmail(passengerDto.getEmail());
        return passengerRepository.save(passenger);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> update(@PathVariable Integer id, @Valid @RequestBody PassengerDto passengerDetails) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + id + " не найден"));

        passenger.setFirstName(passengerDetails.getFirstName());
        passenger.setLastName(passengerDetails.getLastName());
        passenger.setMiddleName(passengerDetails.getMiddleName());
        passenger.setBirthDate(passengerDetails.getBirthDate());
        if (passengerDetails.getGender() != null && !passengerDetails.getGender().isEmpty()) {
            passenger.setGender(passengerDetails.getGender().charAt(0));
        } else {
            passenger.setGender(null);
        }
        passenger.setPassportSeries(passengerDetails.getPassportSeries());
        passenger.setPassportNumber(passengerDetails.getPassportNumber());
        passenger.setPhoneNumber(passengerDetails.getPhoneNumber());
        passenger.setEmail(passengerDetails.getEmail());

        final Passenger updatedPassenger = passengerRepository.save(passenger);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + id + " не найден"));
        passengerRepository.delete(passenger);
        return ResponseEntity.noContent().build();
    }
}