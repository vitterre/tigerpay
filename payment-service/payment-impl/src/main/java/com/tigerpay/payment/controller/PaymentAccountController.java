package com.tigerpay.payment.controller;

import com.tigerpay.payment.api.PaymentAccountApi;
import com.tigerpay.payment.dto.response.PaymentAccountResponseDto;
import com.tigerpay.payment.service.PaymentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentAccountController implements PaymentAccountApi {

    private final PaymentAccountService paymentAccountService;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public List<PaymentAccountResponseDto> getCurrentUserPaymentAccounts() {
        return paymentAccountService.getCurrentUserPaymentAccounts();
    }
}
