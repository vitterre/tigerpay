package com.tigerpay.auth.exception;

import org.springframework.http.HttpStatus;

public class ConflictServiceException extends ServiceException {

    public ConflictServiceException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
