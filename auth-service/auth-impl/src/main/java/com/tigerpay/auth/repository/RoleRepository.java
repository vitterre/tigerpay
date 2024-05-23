package com.tigerpay.auth.repository;

import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountRoleEntity;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Optional<AccountRoleEntity> findByUuid(final UUID uuid);
    Optional<AccountRoleEntity> findByKey(final String key);
}
