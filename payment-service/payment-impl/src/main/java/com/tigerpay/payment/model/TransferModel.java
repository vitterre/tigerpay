package com.tigerpay.payment.model;

import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.enums.TransferCode;

import java.math.BigDecimal;
import java.math.BigInteger;

public record TransferModel(
        BigInteger debitAccountId,
        BigInteger creditAccountId,
        AccountLedger ledger,
        TransferCode code,
        BigDecimal amount
) { }
