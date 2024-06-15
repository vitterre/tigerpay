package com.tigerpay.payment.event;

import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.enums.TransferCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public record TransferEvent(BigInteger transferId,
                            BigInteger receiverAccountId,
                            BigInteger senderAccountId,
                            UUID receiverProfileUuid,
                            UUID senderProfileUuid,
                            BigDecimal amount,
                            AccountLedger currency,
                            TransferCode category) {
}
