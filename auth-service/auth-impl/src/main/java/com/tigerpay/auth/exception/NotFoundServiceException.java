package com.tigerpay.auth.exception;

import org.springframework.http.HttpStatus;

public class NotFoundServiceException extends ServiceException {

    public NotFoundServiceException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
