package com.tigerpay.payment.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public final class AuthenticationHeaderException extends AuthenticationException {

    public AuthenticationHeaderException(final String message) {
        super(message);
    }
}