package com.tigerpay.auth.security.util;

import com.tigerpay.auth.security.exception.AuthenticationHeaderException;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class HttpSettingUtil {

    public static String getTokenFromAuthorizationHeader(final String authorizationHeader) {
        return Optional.ofNullable(authorizationHeader)
                .filter(StringUtils::isNotBlank)
                .map(bearer -> StringUtils.removeStart(bearer, "Bearer ").trim())
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }

    public static String getTokenFromValidatedAuthorizationHeader(final String authorizationHeader) {
        if (Objects.isNull(authorizationHeader)) {
            return null;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new AuthenticationHeaderException("Invalid authentication schema found in Authorization header");
        }

        val token = getTokenFromAuthorizationHeader(authorizationHeader);

        if (Objects.isNull(token)) {
            throw new AuthenticationHeaderException("Authorization header token is empty");
        }

        return token;
    }
}
