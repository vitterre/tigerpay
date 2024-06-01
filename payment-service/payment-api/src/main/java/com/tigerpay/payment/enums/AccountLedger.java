package com.tigerpay.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Getter
@RequiredArgsConstructor
public enum AccountLedger {

    RUB(100),
    USD(200),
    EUR(300);

    private final Integer ledger;

    public static AccountLedger getByLedger(final Integer ledger) {
        for (val accountLedger : values()) {
            if (accountLedger.ledger.equals(ledger)) {
                return accountLedger;
            }
        }
        return null;
    }
}
