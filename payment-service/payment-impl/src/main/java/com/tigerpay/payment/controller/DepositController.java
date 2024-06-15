package com.tigerpay.payment.controller;

import com.tigerpay.payment.api.DepositApi;
import com.tigerpay.payment.dto.request.DepositRequestDto;
import com.tigerpay.payment.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepositController implements DepositApi {

    private final DepositService depositService;

    @Override
    public void deposit(final DepositRequestDto depositRequestDto) {
        depositService.deposit(depositRequestDto);
    }
}
