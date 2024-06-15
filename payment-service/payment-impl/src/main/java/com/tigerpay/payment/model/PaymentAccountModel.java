package com.tigerpay.payment.model;

import com.tigerpay.payment.enums.AccountCode;
import com.tigerpay.payment.enums.AccountLedger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public record PaymentAccountModel(BigInteger id,
                                  AccountCode code,
                                  AccountLedger ledger,
                                  BigDecimal amount,
                                  UUID profileUuid) {
}
