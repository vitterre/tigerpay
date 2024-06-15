package com.tigerpay.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Getter
@RequiredArgsConstructor
public enum TransferCode {

    WITHDRAWAL(1),
    TRANSFERS(2),
    DEPOSIT(3),
    HOUSING(4),
    UTILITIES(5),
    CAFES(6),
    TRANSPORT(7),
    INVESTMENTS(8),
    HEALTHCARE(9),
    BEAUTY(10),
    DEBT(11),
    ENTERTAINMENT(12),
    INSURANCE(13),
    GROCERIES(14),
    TAXES(15),
    SALARY(16);

    private final Integer code;

    public static TransferCode getByCode(final Integer code) {
        for (val transferCode : values()) {
            if (transferCode.code.equals(code)) {
                return transferCode;
            }
        }
        return null;
    }
}
