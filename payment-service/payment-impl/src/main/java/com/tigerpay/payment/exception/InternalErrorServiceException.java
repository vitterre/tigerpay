package com.tigerpay.payment.exception;

import org.springframework.http.HttpStatus;

public class InternalErrorServiceException extends ServiceException {

    public InternalErrorServiceException(final String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
