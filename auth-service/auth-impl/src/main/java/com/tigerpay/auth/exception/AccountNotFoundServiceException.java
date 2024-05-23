package com.tigerpay.auth.exception;

import java.util.UUID;

public final class AccountNotFoundServiceException extends NotFoundServiceException {

    public AccountNotFoundServiceException(final UUID uuid) {
        super("Account with provided uuid %s not found".formatted(uuid));
    }
}
