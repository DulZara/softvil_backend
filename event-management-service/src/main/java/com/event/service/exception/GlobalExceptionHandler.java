package com.event.service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.event.service.dto.ErrorResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource msgSrc;

    public GlobalExceptionHandler(MessageSource msgSrc) {
        this.msgSrc = msgSrc;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, Locale loc) {
        String message = msgSrc.getMessage(ex.getMessage(), null, loc);
        ErrorResponse error = new ErrorResponse(ex.getCode(), message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, Locale loc) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String code = (fieldError != null && fieldError.getDefaultMessage() != null)
                ? fieldError.getDefaultMessage()
                : "validation.error";
        String message = msgSrc.getMessage(code, null, loc);
        ErrorResponse error = new ErrorResponse(code, message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, Locale loc) {
        // For @Validated on method parameters
        String code = "validation.error";
        String message = ex.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse(msgSrc.getMessage(code, null, loc));
        ErrorResponse error = new ErrorResponse(code, message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, Locale loc) {
        String defaultMsg = "An unexpected error occurred";
        String message = msgSrc.getMessage("internal.server.error", null, defaultMsg, loc);
        ErrorResponse error = new ErrorResponse("500", message);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
