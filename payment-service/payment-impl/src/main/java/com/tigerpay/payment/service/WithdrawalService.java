package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.WithdrawalRequestDto;

public interface WithdrawalService {

    void withdrawal(final WithdrawalRequestDto withdrawalRequestDto);
}
