package com.mandarkhanov.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();

        String message = ex.getMostSpecificCause().getMessage();
        if(message.contains("violates foreign key constraint")) {
            message = "Невозможно удалить запись, так как на нее ссылаются другие сущности";
        }
        else if (message.contains("violates unique constraint")) {
            message = "Запись с таким уникальным полем уже существует";
        }
        body.put("message", "Ошибка целостности данных: " + message);

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Произошла непредвиденная ошибка: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}