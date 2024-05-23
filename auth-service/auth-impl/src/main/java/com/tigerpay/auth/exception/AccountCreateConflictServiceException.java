package com.tigerpay.auth.exception;

public class AccountCreateConflictServiceException extends ConflictServiceException {

    public AccountCreateConflictServiceException(final String message) {
        super(message);
    }
}
