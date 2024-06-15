package com.tigerpay.payment.service;

import com.tigerbeetle.*;
import com.tigerpay.payment.dto.response.PaymentAccountResponseDto;
import com.tigerpay.payment.enums.AccountCode;
import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.event.AccountCreatedEvent;
import com.tigerpay.payment.exception.AccountNotFoundServiceException;
import com.tigerpay.payment.model.PaymentAccountModel;
import com.tigerpay.payment.model.jooq.schema.tables.pojos.PaymentAccountEntity;
import com.tigerpay.payment.repository.PaymentAccountRepository;
import com.tigerpay.payment.security.userdetails.Account;
import com.tigerpay.payment.security.util.SecurityGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final Client tigerBeetleClient;
    private final PaymentAccountRepository paymentAccountRepository;

    @KafkaListener(
            id = "accounts-created-events-listener",
            topics = "accounts-created",
            containerFactory = "batchFactory"
    )
    @Override
    public void createPaymentAccounts(final List<AccountCreatedEvent> accountCreatedEvents) throws ConcurrencyExceededException {
        val accounts = new AccountBatch(accountCreatedEvents.size() * AccountLedger.values().length);
        val accountsMetadata = new ArrayList<PaymentAccountEntity>();

        for (val accountCreatedEvent : accountCreatedEvents) {
            val ledgers = AccountLedger.values();

            for (val ledger : ledgers) {
                val generatedId = SecurityGeneratorUtil.generateId();
                val id = UInt128.asBytes(generatedId);
                val code = AccountCode.ASSET.getCode();
                val flags = AccountFlags.HISTORY | AccountFlags.CREDITS_MUST_NOT_EXCEED_DEBITS;

                accounts.add();
                accounts.setId(id);
                accounts.setCode(code);
                accounts.setLedger(ledger.getLedger());
                accounts.setUserData128(UInt128.asBytes(accountCreatedEvent.uuid()));
                accounts.setFlags(flags);

                accountsMetadata.add(
                        new PaymentAccountEntity(
                                generatedId.longValue(),
                                code,
                                ledger.getLedger(),
                                accountCreatedEvent.phoneNumber(),
                                accountCreatedEvent.uuid()
                        )
                );
            }
        }

        System.out.println("CREATING ACCOUNTS: " + accountsMetadata.size());
        tigerBeetleClient.createAccounts(accounts);
        paymentAccountRepository.batchSave(accountsMetadata);
    }

    @SneakyThrows
    @Override
    public PaymentAccountModel getAccountById(final BigInteger id) {
        val idBatch = new IdBatch(1);
        idBatch.add(UInt128.asBytes(id));

        val accounts = tigerBeetleClient.lookupAccounts(idBatch);

        if (!accounts.next()) {
            throw new AccountNotFoundServiceException(id);
        }

        return new PaymentAccountModel(
                UInt128.asBigInteger(accounts.getId()),
                AccountCode.getByCode(accounts.getCode()),
                AccountLedger.getByLedger(accounts.getLedger()),
                new BigDecimal(accounts.getDebitsPosted().subtract(accounts.getCreditsPosted()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY),
                UInt128.asUUID(accounts.getUserData128())
        );
    }

    @SneakyThrows
    @Override
    public List<PaymentAccountResponseDto> getCurrentUserPaymentAccounts() {
        val result = new ArrayList<PaymentAccountResponseDto>();

        val authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        val uuid = ((Account) authentication.getPrincipal()).getUuid();
        val accounts = paymentAccountRepository.getAccountsByProfileUuid(uuid);

        val idBatch = new IdBatch(accounts.size());
        for (val account : accounts) {
            idBatch.add(UInt128.asBytes(account.getId()));
        }


        val foundAccounts = tigerBeetleClient.lookupAccounts(idBatch);

        while (foundAccounts.next()) {
            result.add(
                    new PaymentAccountResponseDto(
                            UInt128.asBigInteger(foundAccounts.getId()),
                            AccountCode.getByCode(foundAccounts.getCode()),
                            AccountLedger.getByLedger(foundAccounts.getLedger()),
                            new BigDecimal(foundAccounts.getDebitsPosted().subtract(foundAccounts.getCreditsPosted()))
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY)
                    )
            );
        }

        return result;
    }
}
