package com.tigerpay.payment.service;

import com.tigerpay.payment.enums.Role;
import com.tigerpay.payment.enums.Subject;
import com.tigerpay.payment.security.exception.AuthenticationHeaderException;
import com.tigerpay.payment.security.userdetails.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

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

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractLogin(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Account extractAccount(final String token) {
        if (!isTokenExpired(token)) {
            val claims = extractAllClaims(token);

            return Account.builder()
                    .uuid(
                            UUID.fromString(claims.get("uuid", String.class))
                    )
                    .username(extractLogin(token))
                    .accessToken(token)
                    .subject(
                            Subject.valueOf(claims.get("am", String.class))
                    )
                    .role(
                            Role.valueOf(claims.get("role", String.class))
                    )
                    .build();
        }

        throw new AuthenticationHeaderException("Token is expired");
    }
}
