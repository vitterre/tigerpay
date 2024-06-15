package com.tigerpay.auth.service;

import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountEntity;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.RefreshTokenEntity;

public interface RefreshTokenService {

    RefreshTokenEntity create(final AccountEntity accountEntity);
    RefreshTokenEntity getByToken(final String token);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken);
    void delete(final String token);
}
