package com.tigerpay.payment.exception;

public class TransferCreationInternalErrorServiceException extends InternalErrorServiceException {

    public TransferCreationInternalErrorServiceException() {
        super("Internal error occurred while transfer creation");
    }
}
