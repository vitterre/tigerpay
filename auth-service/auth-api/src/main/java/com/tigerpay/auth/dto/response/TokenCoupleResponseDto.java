package com.tigerpay.auth.dto.response;

public record TokenCoupleResponseDto(String accessToken,
                                     String refreshToken) {
}
