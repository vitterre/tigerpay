package com.tigerpay.payment.service;

import com.tigerbeetle.ConcurrencyExceededException;
import com.tigerpay.payment.dto.response.PaymentAccountResponseDto;
import com.tigerpay.payment.event.AccountCreatedEvent;
import com.tigerpay.payment.model.PaymentAccountModel;

import java.math.BigInteger;
import java.util.List;

public interface PaymentAccountService {

    void createPaymentAccounts(final List<AccountCreatedEvent> accountCreatedEvents) throws ConcurrencyExceededException;
    PaymentAccountModel getAccountById(final BigInteger id);
    List<PaymentAccountResponseDto> getCurrentUserPaymentAccounts();
}
