package com.tigerpay.payment.repository;

import static com.tigerpay.payment.model.jooq.schema.Tables.*;
import com.tigerpay.payment.model.jooq.schema.tables.pojos.PaymentAccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentAccountJooqRepositoryImpl implements PaymentAccountRepository {

    private final DSLContext jooq;

    @Override
    public void batchSave(final List<PaymentAccountEntity> paymentAccountEntities) {
        val queries = paymentAccountEntities.stream()
                .map(entity ->
                        jooq
                                .insertInto(
                                        PAYMENT_ACCOUNT,
                                        PAYMENT_ACCOUNT.ID,
                                        PAYMENT_ACCOUNT.CODE,
                                        PAYMENT_ACCOUNT.LEDGER,
                                        PAYMENT_ACCOUNT.PHONE_NUMBER,
                                        PAYMENT_ACCOUNT.PROFILE_UUID
                                )
                                .values(
                                        entity.getId(),
                                        entity.getCode(),
                                        entity.getLedger(),
                                        entity.getPhoneNumber(),
                                        entity.getProfileUuid()
                                )
                )
                .toList();

        jooq.batch(queries)
                .execute();
    }

    @Override
    public List<PaymentAccountEntity> getAccountsByProfileUuid(final UUID uuid) {
        return jooq
                .selectFrom(PAYMENT_ACCOUNT)
                .where(PAYMENT_ACCOUNT.PROFILE_UUID.eq(uuid))
                .fetchInto(PaymentAccountEntity.class);
    }
}
