package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.enums.Subject;
import com.tigerpay.auth.dto.response.AccountResponseDto;
import com.tigerpay.auth.security.userdetails.Account;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface JwtService {

    String extractLogin(final String token);
    Account extractAccount(final String token);
    Subject extractAuthorizationMethod(final String token);
    Date extractExpiration(final String token);
    Boolean isTokenExpired(final String token);
    String generateToken(final String login, final Map<String, Object> data);
    Boolean isTokenValid(String accessToken, String login, Subject subject);
}
