package com.tigerpay.auth.exception;

public class RefreshTokenNotFoundServiceException extends NotFoundServiceException {

    public RefreshTokenNotFoundServiceException(final String token) {
        super("Refresh token %s not found".formatted(token));
    }
}
