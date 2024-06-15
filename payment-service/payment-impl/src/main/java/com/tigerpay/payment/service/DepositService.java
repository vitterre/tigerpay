package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.DepositRequestDto;

public interface DepositService {

    void deposit(final DepositRequestDto depositRequestDto);
}
