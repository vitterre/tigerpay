package com.tigerpay.payment.security.util;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.security.SecureRandom;

@UtilityClass
public class SecurityGeneratorUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static BigInteger generateId() {
        return generateId(64);
    }

    public static BigInteger generateId(final int numBits) {
        return new BigInteger(numBits, SECURE_RANDOM);
    }
}
