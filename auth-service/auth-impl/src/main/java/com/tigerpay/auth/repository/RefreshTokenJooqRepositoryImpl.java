package com.tigerpay.auth.repository;

import static com.tigerpay.auth.model.jooq.schema.Tables.*;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenJooqRepositoryImpl implements RefreshTokenRepository {

    private final DSLContext jooq;

    @Override
    public Optional<RefreshTokenEntity> findByToken(final String token) {
        return jooq
                .selectFrom(REFRESH_TOKEN)
                .where(REFRESH_TOKEN.TOKEN.eq(token))
                .fetchOptionalInto(RefreshTokenEntity.class);
    }

    @Override
    public RefreshTokenEntity save(final RefreshTokenEntity refreshToken) {
        return jooq
                .insertInto(
                        REFRESH_TOKEN,
                        REFRESH_TOKEN.ACCOUNT_UUID,
                        REFRESH_TOKEN.TOKEN,
                        REFRESH_TOKEN.EXPIRES_AT
                )
                .values(
                        refreshToken.getAccountUuid(),
                        refreshToken.getToken(),
                        refreshToken.getExpiresAt()
                )
                .returningResult(REFRESH_TOKEN)
                .fetchOneInto(RefreshTokenEntity.class);

    }

    @Override
    public void delete(final RefreshTokenEntity refreshToken) {
        jooq
                .deleteFrom(REFRESH_TOKEN)
                .where(REFRESH_TOKEN.TOKEN.eq(refreshToken.getToken()))
                .execute();
    }
}
