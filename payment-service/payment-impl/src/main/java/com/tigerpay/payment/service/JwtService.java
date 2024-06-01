package com.tigerpay.payment.service;

import com.tigerpay.payment.security.userdetails.Account;

public interface JwtService {
    Account extractAccount(final String token);
}
