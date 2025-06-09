package com.mandarkhanov.service;

import com.mandarkhanov.dto.AuthResponse;
import com.mandarkhanov.dto.LoginRequest;
import com.mandarkhanov.dto.RegisterRequest;
import com.mandarkhanov.model.Role;
import com.mandarkhanov.model.User;
import com.mandarkhanov.repository.UserRepository;
import com.mandarkhanov.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER) // По умолчанию все регистрируются как USER
                .build();
        repository.save(user);

        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(); // Здесь можно добавить более кастомную ошибку

        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}