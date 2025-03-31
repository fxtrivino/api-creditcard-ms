package com.bci.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidDecryptDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDecryptDataException(InvalidDecryptDataException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(InvalidEncryptDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEncryptDataException(InvalidEncryptDataException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(InvalidGenerationKeyAESException.class)
    public ResponseEntity<Map<String, String>> handleInvalidGenerationKeyAESException(InvalidGenerationKeyAESException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
}
