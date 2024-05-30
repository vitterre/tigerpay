package com.tigerpay.auth.security.filter;

import com.tigerpay.auth.service.AccountService;
import com.tigerpay.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHENTICATION_HEADER = "Authorization";

    private final JwtService jwtService;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        val authHeader = request.getHeader(AUTHENTICATION_HEADER);

        if (Objects.isNull(authHeader) || authHeader.isBlank() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        val accessToken = authHeader.substring(BEARER_PREFIX.length());
        val login = jwtService.extractLogin(accessToken);
        val subject = jwtService.extractAuthorizationMethod(accessToken);

        if (!login.isBlank() && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            val account = accountService.getByLogin(subject, login);

            if (jwtService.isTokenValid(accessToken, login, subject)) {
                val context = SecurityContextHolder.createEmptyContext();

                val authenticationToken = new PreAuthenticatedAuthenticationToken(account, null, account.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
