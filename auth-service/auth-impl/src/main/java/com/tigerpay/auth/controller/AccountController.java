package com.tigerpay.auth.controller;

import com.tigerpay.auth.api.AccountApi;
import com.tigerpay.auth.dto.response.AccountResponseDto;
import com.tigerpay.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public AccountResponseDto getCurrentUserAccount() {
        return accountService.getCurrentUserAccount();
    }
}
