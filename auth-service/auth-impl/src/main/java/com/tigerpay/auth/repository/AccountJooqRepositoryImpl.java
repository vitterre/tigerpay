package com.tigerpay.auth.repository;

import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.tigerpay.auth.model.jooq.schema.Tables.ACCOUNT;

@Repository
@RequiredArgsConstructor
public class AccountJooqRepositoryImpl implements AccountRepository {

    private final DSLContext jooq;

    @Override
    public AccountEntity save(final AccountEntity accountEntity) {
        return jooq
                .insertInto(
                        ACCOUNT,
                        ACCOUNT.FIRST_NAME,
                        ACCOUNT.LAST_NAME,
                        ACCOUNT.MIDDLE_NAME,
                        ACCOUNT.EMAIL_ADDRESS,
                        ACCOUNT.PHONE_NUMBER,
                        ACCOUNT.PASSWORD,
                        ACCOUNT.ROLE_UUID
                )
                .values(
                        accountEntity.getFirstName(),
                        accountEntity.getLastName(),
                        accountEntity.getMiddleName(),
                        accountEntity.getEmailAddress(),
                        accountEntity.getPhoneNumber(),
                        accountEntity.getPassword(),
                        accountEntity.getRoleUuid()
                )
                .returningResult(ACCOUNT)
                .fetchOneInto(AccountEntity.class);
    }

    @Override
    public Optional<AccountEntity> findByUuid(final UUID uuid) {
        return jooq
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.UUID.eq(uuid))
                .fetchOptionalInto(AccountEntity.class);
    }

    @Override
    public Optional<AccountEntity> findByPhoneNumber(final String phoneNumber) {
        return jooq
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.PHONE_NUMBER.eq(phoneNumber))
                .fetchOptionalInto(AccountEntity.class);
    }

    @Override
    public Optional<AccountEntity> findByEmailAddress(final String emailAddress) {
        return jooq
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.EMAIL_ADDRESS.eq(emailAddress))
                .fetchOptionalInto(AccountEntity.class);
    }
}
