package com.tigerpay.auth.exception;

import java.util.UUID;

public final class RoleNotFoundServiceException extends NotFoundServiceException {

    public RoleNotFoundServiceException(final UUID uuid) {
        super("Role with provided uuid %s not found".formatted(uuid));
    }

    public RoleNotFoundServiceException(final String message) {
        super(message);
    }
}
