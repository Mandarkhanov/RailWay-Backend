package com.mandarkhanov.controller;

import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Ticket;
import com.mandarkhanov.model.User;
import com.mandarkhanov.repository.UserRepository;
import com.mandarkhanov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getMyTickets() {
        User currentUser = getCurrentUser();
        List<Ticket> tickets = userService.findTicketsForUser(currentUser);
        return ResponseEntity.ok(tickets);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + currentPrincipalName));
    }
}