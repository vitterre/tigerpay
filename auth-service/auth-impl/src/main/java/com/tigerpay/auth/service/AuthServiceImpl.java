package com.tigerpay.auth.service;

import com.tigerpay.auth.dto.enums.Role;
import com.tigerpay.auth.dto.enums.Subject;
import com.tigerpay.auth.dto.request.AccountLoginRequestDto;
import com.tigerpay.auth.dto.request.AccountRegisterRequestDto;
import com.tigerpay.auth.dto.request.RefreshTokenRequestDto;
import com.tigerpay.auth.dto.response.TokenCoupleResponseDto;
import com.tigerpay.auth.exception.AccountCreateConflictServiceException;
import com.tigerpay.auth.exception.AccountNotFoundServiceException;
import com.tigerpay.auth.exception.RoleNotFoundServiceException;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountEntity;
import com.tigerpay.auth.model.jooq.schema.tables.pojos.AccountRoleEntity;
import com.tigerpay.auth.repository.AccountRepository;
import com.tigerpay.auth.repository.RoleRepository;
import com.tigerpay.auth.security.exception.AuthenticationHeaderException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public final class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    private TokenCoupleResponseDto authenticate(
            final Subject subject,
            final AccountEntity accountEntity,
            final AccountRoleEntity accountRoleEntity
    ) {
        return new TokenCoupleResponseDto(
                jwtService.generateToken(
                        subject.equals(Subject.PHONE_NUMBER) ?
                                accountEntity.getPhoneNumber() :
                                accountEntity.getEmailAddress(),
                        Collections.singletonMap("role", accountRoleEntity.getKey())
                ),
                refreshTokenService.create(accountEntity).getToken()
        );
    }

    @Override
    public TokenCoupleResponseDto register(final AccountRegisterRequestDto accountRegisterRequestDto) {
        accountRepository
                .findByPhoneNumber(accountRegisterRequestDto.getPhoneNumber())
                .ifPresent(account -> {
                    throw new AccountCreateConflictServiceException(
                            "Account with provided phone number %s already exists"
                                    .formatted(accountRegisterRequestDto.getPhoneNumber())
                    );
                });

        accountRepository
                .findByEmailAddress(accountRegisterRequestDto.getEmailAddress())
                .ifPresent(account -> {
                    throw new AccountCreateConflictServiceException(
                            "Account with provided email address %s already exists"
                                    .formatted(accountRegisterRequestDto.getEmailAddress())
                    );
                });

        val encryptedPassword = passwordEncoder.encode(accountRegisterRequestDto.getPassword());
        val role = roleRepository
                .findByKey(Role.USER.name())
                .orElseThrow(() -> new RoleNotFoundServiceException(Role.USER.name()));

        val account = new AccountEntity();

        account.setFirstName(accountRegisterRequestDto.getFirstName());
        account.setLastName(accountRegisterRequestDto.getLastName());
        account.setMiddleName(accountRegisterRequestDto.getMiddleName());
        account.setEmailAddress(accountRegisterRequestDto.getEmailAddress());
        account.setPhoneNumber(accountRegisterRequestDto.getPhoneNumber());
        account.setPassword(encryptedPassword);
        account.setRoleUuid(role.getUuid());

        val savedAccount = accountRepository.save(account);
        return authenticate(Subject.PHONE_NUMBER, savedAccount, role);
    }

    @Override
    public TokenCoupleResponseDto login(final AccountLoginRequestDto accountLoginRequestDto) {

        val subject = Subject.valueOf(accountLoginRequestDto.getSubject());
        val login = accountLoginRequestDto.getLogin();

        val accountEntity = (Subject.PHONE_NUMBER.equals(subject) ?
                accountRepository.findByPhoneNumber(login) :
                accountRepository.findByEmailAddress(login)
        ).orElseThrow(() -> new AuthenticationHeaderException("Wrong credentials provided"));

        val roleEntity = roleRepository
                .findByUuid(accountEntity.getRoleUuid())
                .orElseThrow(() -> new RoleNotFoundServiceException(accountEntity.getRoleUuid()));

        if (!passwordEncoder.matches(accountLoginRequestDto.getPassword(), accountEntity.getPassword())) {
            throw new AuthenticationHeaderException("Wrong credentials provided");
        }

        return authenticate(subject, accountEntity, roleEntity);
    }

    @Override
    public TokenCoupleResponseDto refresh(final RefreshTokenRequestDto refreshTokenRequestDto) {
        val oldRefreshTokenEntity = refreshTokenService
                .verifyExpiration(refreshTokenService.getByToken(refreshTokenRequestDto.getRefreshToken()));

        val accountEntity = accountRepository
                .findByUuid(oldRefreshTokenEntity.getAccountUuid())
                .orElseThrow(() ->
                        new AccountNotFoundServiceException(oldRefreshTokenEntity.getAccountUuid())
                );

        val roleEntity = roleRepository
                .findByUuid(accountEntity.getRoleUuid())
                .orElseThrow(() -> new RoleNotFoundServiceException(accountEntity.getRoleUuid()));

        refreshTokenService.delete(refreshTokenRequestDto.getRefreshToken());

        return new TokenCoupleResponseDto(
                jwtService.generateToken(
                        accountEntity.getPhoneNumber(),
                        Collections.singletonMap("role", roleEntity.getLabel())
                ),
                refreshTokenService.create(accountEntity).getToken()
        );
    }
}
