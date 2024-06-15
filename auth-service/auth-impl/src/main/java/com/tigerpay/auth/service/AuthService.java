package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.request.AccountLoginRequestDto;
import com.tigerpay.auth.dto.request.AccountRegisterRequestDto;
import com.tigerpay.auth.dto.request.RefreshTokenRequestDto;
import com.tigerpay.auth.dto.response.TokenCoupleResponseDto;

public interface AuthService {

    TokenCoupleResponseDto register(final AccountRegisterRequestDto accountRegisterRequestDto);
    TokenCoupleResponseDto login(final AccountLoginRequestDto accountLoginRequestDto);
    TokenCoupleResponseDto refresh(final RefreshTokenRequestDto refreshTokenRequestDto);
}
