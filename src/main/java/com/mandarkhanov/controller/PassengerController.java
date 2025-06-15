package com.mandarkhanov.controller;

import com.mandarkhanov.dto.PassengerDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Passenger;
import com.mandarkhanov.repository.PassengerRepository;
import com.mandarkhanov.service.PassengerSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public Iterable<Passenger> getAll(
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) Boolean departedAbroad,
            @RequestParam(required = false) Boolean hasLuggage,
            @RequestParam(required = false) Character gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        Specification<Passenger> spec = createSpecification(scheduleId, departureDate, departedAbroad, hasLuggage, gender, minAge, maxAge);
        return passengerRepository.findAll(spec);
    }

    @GetMapping("/count")
    public Map<String, Long> getCount(
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) Boolean departedAbroad,
            @RequestParam(required = false) Boolean hasLuggage,
            @RequestParam(required = false) Character gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        Specification<Passenger> spec = createSpecification(scheduleId, departureDate, departedAbroad, hasLuggage, gender, minAge, maxAge);
        return Map.of("count", passengerRepository.count(spec));
    }

    private Specification<Passenger> createSpecification(Integer scheduleId, LocalDate departureDate, Boolean departedAbroad,
                                                         Boolean hasLuggage, Character gender, Integer minAge, Integer maxAge) {
        Specification<Passenger> spec = Specification.where(PassengerSpecification.forSchedule(scheduleId));

        if (Boolean.TRUE.equals(departedAbroad) && departureDate != null) {
            spec = spec.and(PassengerSpecification.departedAbroadOnDate(departureDate));
        } else if (departureDate != null) {
            spec = spec.and(PassengerSpecification.departedOnDate(departureDate));
        }

        return spec.and(PassengerSpecification.hasLuggage(hasLuggage))
                .and(PassengerSpecification.hasGender(gender))
                .and(PassengerSpecification.isAgeBetween(minAge, maxAge));
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