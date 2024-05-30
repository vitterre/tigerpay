package com.tigerpay.auth.security.filter;

import com.tigerpay.auth.security.util.HttpResponseUtil;
import com.tigerpay.auth.security.util.HttpSettingUtil;
import com.tigerpay.auth.service.AccountService;
import com.tigerpay.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class TokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private final AccountService accountService;
    private final JwtService jwtService;

    public TokenAuthenticationFilter(final AccountService accountService, final JwtService jwtService, final AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            val token = HttpSettingUtil.getTokenFromValidatedAuthorizationHeader(((HttpServletRequest) request).getHeader("Authorization"));

            if (Objects.nonNull(token)) {
                val login = jwtService.extractLogin(token);
                val subject = jwtService.extractAuthorizationMethod(token);
                val account = accountService.getByLogin(subject, login);

                val authenticationToken = new PreAuthenticatedAuthenticationToken(account, token, account.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(
                        authenticationToken
                );
            }

            chain.doFilter(request, response);
        } catch (Exception exception) {
            HttpResponseUtil.putExceptionInResponse(((HttpServletRequest) request), ((HttpServletResponse) response),
                    exception, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
