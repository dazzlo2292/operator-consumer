package ru.app.web.controllers.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(new ErrorView(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorView(e.getClass().getSimpleName(), e.getMessage()));
    }
}
