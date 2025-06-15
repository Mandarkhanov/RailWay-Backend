package com.mandarkhanov.controller;

import com.mandarkhanov.dto.PassengerDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Passenger;
import com.mandarkhanov.model.User;
import com.mandarkhanov.model.UserPassenger;
import com.mandarkhanov.repository.PassengerRepository;
import com.mandarkhanov.repository.UserPassengerRepository;
import com.mandarkhanov.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/passengers")
public class UserPassengerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private UserPassengerRepository userPassengerRepository;

    @GetMapping
    public ResponseEntity<List<Passenger>> getMyPassengers() {
        User currentUser = getCurrentUser();
        List<Passenger> passengers = userPassengerRepository.findPassengersByUserId(currentUser.getId());
        return ResponseEntity.ok(passengers);
    }

    @PostMapping
    public ResponseEntity<Passenger> addPassengerToMyList(@Valid @RequestBody PassengerDto passengerDto) {
        User currentUser = getCurrentUser();

        Passenger newPassenger = new Passenger();
        newPassenger.setFirstName(passengerDto.getFirstName());
        newPassenger.setLastName(passengerDto.getLastName());
        newPassenger.setMiddleName(passengerDto.getMiddleName());
        newPassenger.setBirthDate(passengerDto.getBirthDate());
        if (passengerDto.getGender() != null && !passengerDto.getGender().isEmpty()) {
            newPassenger.setGender(passengerDto.getGender().charAt(0));
        }
        newPassenger.setPassportSeries(passengerDto.getPassportSeries());
        newPassenger.setPassportNumber(passengerDto.getPassportNumber());
        newPassenger.setPhoneNumber(passengerDto.getPhoneNumber());
        newPassenger.setEmail(passengerDto.getEmail());
        Passenger savedPassenger = passengerRepository.save(newPassenger);

        UserPassenger userPassenger = new UserPassenger(currentUser, savedPassenger);
        userPassengerRepository.save(userPassenger);

        return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    }


    @DeleteMapping("/{passengerId}")
    public ResponseEntity<?> removePassengerFromMyList(@PathVariable Integer passengerId) {
        User currentUser = getCurrentUser();

        UserPassenger userPassenger = userPassengerRepository.findByUserIdAndPassengerId(currentUser.getId(), passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Пассажир с ID " + passengerId + " не привязан к вашему аккаунту"));

        userPassengerRepository.delete(userPassenger);

        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + currentPrincipalName));
    }
}