package com.tigerpay.payment.service;

import com.tigerbeetle.ConcurrencyExceededException;
import com.tigerpay.payment.dto.response.PaymentAccountResponseDto;
import com.tigerpay.payment.event.AccountCreatedEvent;

import java.util.List;

public interface PaymentAccountService {

    void createPaymentAccounts(final List<AccountCreatedEvent> accountCreatedEvents) throws ConcurrencyExceededException;
    List<PaymentAccountResponseDto> getCurrentUserPaymentAccounts();
}
