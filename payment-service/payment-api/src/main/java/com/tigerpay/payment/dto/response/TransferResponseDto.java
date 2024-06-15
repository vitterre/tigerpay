package com.tigerpay.payment.dto.response;

import com.tigerpay.payment.enums.AccountLedger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public record TransferResponseDto(BigInteger id,
                                  BigInteger receiverId,
                                  BigInteger senderId,
                                  BigDecimal amount,
                                  AccountLedger ledger,
                                  String category,
                                  Date issuedAt) {
}
