package com.development.Address.Book.App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.development.Address.Book.App.exception.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        String errorMessage = "Validation failed";
        String errorDetails = errors.toString();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressBookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressBookNotFoundException(AddressBookNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("User not found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}