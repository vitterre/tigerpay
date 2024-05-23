package com.tigerpay.auth.exception;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionMessage> handleServiceException(final ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus())
                .body(new ExceptionMessage(
                        serviceException.getMessage(),
                        serviceException.getClass().getSimpleName()
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage handleAuthenticationException(final AuthenticationException authenticationException) {
        return new ExceptionMessage(
                authenticationException.getMessage(),
                authenticationException.getClass().getSimpleName()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleAnyException(final Exception exception) {
        return new ExceptionMessage(
                exception.getMessage(),
                exception.getClass().getSimpleName()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        val errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            val fieldName = ((FieldError) error).getField();
            val errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
