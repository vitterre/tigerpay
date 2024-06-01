package com.tigerpay.payment.exception;

public class TransferBadRequestServiceException extends BadRequestServiceException {

    public TransferBadRequestServiceException() {
        super("Credits must not exceed debits");
    }
}
