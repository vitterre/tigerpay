package com.tigerpay.payment.exception;

import java.math.BigInteger;

public class AccountNotFoundServiceException extends NotFoundServiceException {

    public AccountNotFoundServiceException(final BigInteger id) {
        super("Account with provided id %s not found".formatted(id));
    }
}
