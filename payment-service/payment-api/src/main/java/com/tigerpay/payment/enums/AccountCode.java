package com.tigerpay.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Getter
@RequiredArgsConstructor
public enum AccountCode {

    ASSET(10),
    LIABILITY(20),
    EQUITY(30),
    INCOME(40),
    EXPENSE(50);

    private final Integer code;

    public static AccountCode getByCode(final Integer code) {
        for (val accountCode : values()) {
            if (accountCode.code.equals(code)) {
                return accountCode;
            }
        }
        return null;
    }
}
