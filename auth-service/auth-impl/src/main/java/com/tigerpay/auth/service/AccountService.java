package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.response.AccountResponseDto;

public interface AccountService {

    AccountResponseDto getCurrentUserAccount();
}
