package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.enums.Role;
import com.tigerpay.auth.dto.enums.Subject;
import com.tigerpay.auth.dto.response.AccountResponseDto;
import com.tigerpay.auth.exception.AccountNotFoundServiceException;
import com.tigerpay.auth.exception.RoleNotFoundServiceException;
import com.tigerpay.auth.repository.AccountRepository;
import com.tigerpay.auth.repository.RoleRepository;
import com.tigerpay.auth.security.userdetails.Account;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public AccountResponseDto getCurrentUserAccount() {
        val authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        val account = (Account) authentication.getPrincipal();
        val accountEntity = accountRepository
                .findByUuid(account.getUuid())
                .orElseThrow(() -> new AccountNotFoundServiceException(account.getUuid()));

        val roleEntity = roleRepository
                .findByUuid(accountEntity.getRoleUuid())
                .orElseThrow(() -> new RoleNotFoundServiceException(accountEntity.getRoleUuid()));

        return new AccountResponseDto(
                accountEntity.getUuid(),
                accountEntity.getFirstName(),
                accountEntity.getLastName(),
                accountEntity.getMiddleName(),
                accountEntity.getEmailAddress(),
                accountEntity.getPhoneNumber(),
                Role.valueOf(roleEntity.getKey())
        );
    }
}
