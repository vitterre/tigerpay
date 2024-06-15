package com.tigerpay.auth.repository;

import com.tigerpay.auth.model.jooq.schema.tables.pojos.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshTokenEntity> findByToken(final String token);
    RefreshTokenEntity save(final RefreshTokenEntity refreshToken);
    void delete(final RefreshTokenEntity refreshToken);
}
