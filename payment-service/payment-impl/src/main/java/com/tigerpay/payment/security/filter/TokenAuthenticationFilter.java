package com.tigerpay.payment.security.filter;

import com.tigerpay.payment.security.util.HttpResponseUtil;
import com.tigerpay.payment.security.util.HttpSettingUtil;
import com.tigerpay.payment.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

public final class TokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private final JwtService jwtService;

    public TokenAuthenticationFilter(final JwtService jwtService, final AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            val token = HttpSettingUtil.getTokenFromValidatedAuthorizationHeader(((HttpServletRequest) request).getHeader("Authorization"));

            if (Objects.nonNull(token)) {
                val account = jwtService.extractAccount(token);
                val authenticationToken = new PreAuthenticatedAuthenticationToken(account, token, account.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            chain.doFilter(request, response);
        } catch (Exception exception) {
            HttpResponseUtil.putExceptionInResponse(
                    (HttpServletRequest) request,
                    (HttpServletResponse) response,
                    exception,
                    HttpServletResponse.SC_UNAUTHORIZED
            );
        }
    }
}
