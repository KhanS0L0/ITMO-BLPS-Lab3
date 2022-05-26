package com.example.controller.advices;

import com.example.exception.UserAlreadyExistException;
import com.example.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity handleRegistrationException(UserAlreadyExistException e){
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleAuthenticationException(UserNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleAuthenticationException(UsernameNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
