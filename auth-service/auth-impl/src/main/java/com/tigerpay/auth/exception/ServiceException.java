package com.tigerpay.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ServiceException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
