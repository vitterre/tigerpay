package com.tigerpay.payment.exception;

import org.springframework.http.HttpStatus;

public class BadRequestServiceException extends ServiceException {

    public BadRequestServiceException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
