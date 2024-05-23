package com.tigerpay.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public final class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.access}")
    private Long expirationAccess;

    private SecretKey getSecretKey() {
        val keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractLogin(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String generateToken(final String login, final Map<String, Object> data) {
        return Jwts.builder()
                .claims(data)
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plusMillis(expirationAccess)))
                .signWith(getSecretKey())
                .compact();
    }
}
