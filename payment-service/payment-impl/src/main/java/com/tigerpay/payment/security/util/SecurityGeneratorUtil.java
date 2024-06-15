package com.tigerpay.payment.security.util;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.security.SecureRandom;

@UtilityClass
public class SecurityGeneratorUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static BigInteger generateId() {
        return generateId(32);
    }

    public static BigInteger generateId(final int numBits) {
        BigInteger id;

        do {
            id = new BigInteger(numBits, SECURE_RANDOM);
        } while (id.signum() == -1);

        return id;
    }
}
