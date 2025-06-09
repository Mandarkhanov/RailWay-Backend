package com.mandarkhanov;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCheck {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String plainUserPassword = "password";
        String hashedUserPassword = "$2a$10$MXH1Y7UBVVMSeRa5KHhCuuMO.rKYR3WEl3QY0p74mNXkx8Jrv9eyy";

        String plainAdminPassword = "admin";
        String hashedAdminPassword = "$2a$10$l7gfi6YB/xRuLbv7PCy7M.2ugAh8N4FIE3RhbcZE.X4n7OYyPdAw.";

        boolean isUserPasswordMatch = passwordEncoder.matches(plainUserPassword, hashedUserPassword);
        boolean isAdminPasswordMatch = passwordEncoder.matches(plainAdminPassword, hashedAdminPassword);

        System.out.println("Проверяем пароль для обычного пользователя ('password')...");
        System.out.println("Хэш в базе: " + hashedUserPassword);
        System.out.println("Совпадение: " + isUserPasswordMatch);
        System.out.println("---");

        System.out.println("Проверяем пароль для администратора ('admin')...");
        System.out.println("Хэш в базе: " + hashedAdminPassword);
        System.out.println("Совпадение: " + isAdminPasswordMatch);
        System.out.println("---");
    }
}
