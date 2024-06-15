package com.tigerpay.auth.repository;

import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountEntity;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    AccountEntity save(final AccountEntity accountEntity);
    Optional<AccountEntity> findByUuid(final UUID uuid);
    Optional<AccountEntity> findByPhoneNumber(final String phoneNumber);
    Optional<AccountEntity> findByEmailAddress(final String emailAddress);
}
