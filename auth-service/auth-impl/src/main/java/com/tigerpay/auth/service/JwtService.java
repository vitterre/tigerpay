package com.tigerpay.auth.service;

import java.util.Date;
import java.util.Map;

public interface JwtService {

    String extractLogin(final String token);
    Date extractExpiration(final String token);
    String generateToken(final String login, final Map<String, Object> data);
}
