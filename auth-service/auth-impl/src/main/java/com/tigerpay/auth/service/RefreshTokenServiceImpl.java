package com.tigerpay.auth.service;

import com.tigerpay.auth.exception.RefreshTokenNotFoundServiceException;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountEntity;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.RefreshTokenEntity;
import com.tigerpay.auth.repository.RefreshTokenRepository;
import com.tigerpay.auth.security.exception.AuthenticationHeaderException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.expiration.refresh}")
    private Long expirationRefresh;

    public RefreshTokenEntity create(final AccountEntity accountEntity) {
        val refreshToken = new RefreshTokenEntity();

        refreshToken.setAccountUuid(accountEntity.getUuid());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Timestamp.from(Instant.now().plusMillis(expirationRefresh)).toLocalDateTime());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshTokenEntity getByToken(final String token) {
        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundServiceException(token));
    }

    @Override
    public RefreshTokenEntity verifyExpiration(final RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthenticationHeaderException(
                    "Refresh token %s is expired".formatted(refreshToken.getToken())
            );
        }
        return refreshToken;
    }

    @Override
    public void delete(final String token) {
        refreshTokenRepository
                .findByToken(token)
                .ifPresent(refreshTokenRepository::delete);
    }
}
