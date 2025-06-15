package com.mandarkhanov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.mandarkhanov.model.Role.ROLE_ADMIN;
import static com.mandarkhanov.model.Role.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/auth/**", "/hello").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers("/api/admin/**").hasAuthority(ROLE_ADMIN.name())
                                .requestMatchers("/api/user/**").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())
                                .requestMatchers(HttpMethod.GET).hasAnyAuthority(ROLE_ADMIN.name(), ROLE_USER.name())
                                .requestMatchers(HttpMethod.POST, "/api/user/passengers").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/tickets").hasAnyAuthority(ROLE_USER.name(), ROLE_ADMIN.name())
                                .requestMatchers(HttpMethod.POST).hasAuthority(ROLE_ADMIN.name())
                                .requestMatchers(HttpMethod.PUT).hasAuthority(ROLE_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE).hasAuthority(ROLE_ADMIN.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}