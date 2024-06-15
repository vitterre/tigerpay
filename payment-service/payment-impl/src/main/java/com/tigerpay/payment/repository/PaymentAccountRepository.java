package com.tigerpay.payment.repository;

import com.tigerpay.payment.model.jooq.schema.tables.pojos.PaymentAccountEntity;

import java.util.List;
import java.util.UUID;

public interface PaymentAccountRepository {

    void batchSave(final List<PaymentAccountEntity> paymentAccountEntities);
    List<PaymentAccountEntity> getAccountsByProfileUuid(final UUID uuid);
}
