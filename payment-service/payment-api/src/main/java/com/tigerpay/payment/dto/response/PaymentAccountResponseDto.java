package com.tigerpay.payment.dto.response;

import com.tigerpay.payment.enums.AccountCode;
import com.tigerpay.payment.enums.AccountLedger;

import java.math.BigDecimal;
import java.math.BigInteger;

public record PaymentAccountResponseDto(BigInteger id,
                                        AccountCode code,
                                        AccountLedger ledger,
                                        BigDecimal balance) {
}
