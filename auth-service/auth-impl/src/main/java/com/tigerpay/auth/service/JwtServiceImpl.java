package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.enums.Role;
import com.tigerpay.auth.dto.enums.Subject;
import com.tigerpay.auth.dto.response.AccountResponseDto;
import com.tigerpay.auth.security.exception.AuthenticationHeaderException;
import com.tigerpay.auth.security.userdetails.Account;
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
import java.util.UUID;
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
    public Subject extractAuthorizationMethod(final String token) {
        return extractClaim(token, claims -> {
            val subject = (String) Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("am");
            return Subject.valueOf(subject);
        });
    }

    @Override
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
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

    @Override
    public Boolean isTokenValid(final String accessToken, final String login, final Subject subject) {
        val jwtLogin = extractLogin(accessToken);
        val jwtSubject = extractAuthorizationMethod(accessToken);
        return jwtLogin.equals(login) && jwtSubject.equals(subject) && !isTokenExpired(accessToken);
    }
}
