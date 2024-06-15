package com.tigerpay.payment.controller;

import com.tigerpay.payment.api.WithdrawalApi;
import com.tigerpay.payment.dto.request.WithdrawalRequestDto;
import com.tigerpay.payment.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WithdrawalController implements WithdrawalApi {

    private final WithdrawalService withdrawalService;

    @Override
    public void withdrawal(final WithdrawalRequestDto withdrawalRequestDto) {
        withdrawalService.withdrawal(withdrawalRequestDto);
    }
}
