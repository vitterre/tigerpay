package com.tigerpay.auth.security.userdetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        return (Account) token.getPrincipal();
    }

    //    private UserDetails loadUserDetails(final AccountResponseDto accountResponseDto, final String accessToken) {
    //        log.debug("Loading user details from {}", accountResponseDto);
    //
    //        try {
    //            return Optional.ofNullable(accountResponseDto)
    //                    .map(account ->
    //                            Account.builder()
    //                                    .uuid(account.uuid())
    //                                    .subject(Subject.PHONE_NUMBER)
    //                                    .username(accountResponseDto.phoneNumber())
    //                                    .role(accountResponseDto.role())
    //                                    .accessToken(accessToken)
    //                                    .build()
    //                    ).orElseThrow(() ->
    //                            new AuthenticationHeaderException("Unknown user account by token %s".formatted(accessToken))
    //                    );
    //        } catch (Exception e) {
    //            throw new AuthenticationHeaderException(e.getMessage());
    //        }
    //    }
}
