package com.tigerpay.auth.repository;

import static com.tigerpay.auth.model.jooq.schema.Tables.*;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountRoleEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleJooqRepositoryImpl implements RoleRepository {

    private final DSLContext jooq;

    @Override
    public Optional<AccountRoleEntity> findByUuid(final UUID uuid) {
        return jooq
                .selectFrom(ACCOUNT_ROLE)
                .where(ACCOUNT_ROLE.UUID.eq(uuid))
                .fetchOptionalInto(AccountRoleEntity.class);
    }

    @Override
    public Optional<AccountRoleEntity> findByKey(final String key) {
        return jooq
                .selectFrom(ACCOUNT_ROLE)
                .where(ACCOUNT_ROLE.KEY.eq(key))
                .fetchOptionalInto(AccountRoleEntity.class);
    }
}
