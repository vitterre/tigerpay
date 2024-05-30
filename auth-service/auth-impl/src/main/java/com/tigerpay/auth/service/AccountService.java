package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.enums.Subject;
import com.tigerpay.auth.dto.response.AccountResponseDto;
import com.tigerpay.auth.security.userdetails.Account;

public interface AccountService {

    Account getByLogin(final Subject subject, final String login);
    AccountResponseDto getCurrentUserAccount();
}
