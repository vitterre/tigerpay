package com.tigerpay.auth.controller;

import com.tigerpay.auth.api.AuthApi;
import com.tigerpay.auth.dto.request.AccountLoginRequestDto;
import com.tigerpay.auth.dto.request.AccountRegisterRequestDto;
import com.tigerpay.auth.dto.request.RefreshTokenRequestDto;
import com.tigerpay.auth.dto.response.TokenCoupleResponseDto;
import com.tigerpay.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public TokenCoupleResponseDto register(final AccountRegisterRequestDto accountRegisterRequestDto) {
        return authService.register(accountRegisterRequestDto);
    }

    @Override
    public TokenCoupleResponseDto login(final AccountLoginRequestDto accountLoginRequestDto) {
        return authService.login(accountLoginRequestDto);
    }

    @Override
    public TokenCoupleResponseDto refresh(final RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.refresh(refreshTokenRequestDto);
    }
}
