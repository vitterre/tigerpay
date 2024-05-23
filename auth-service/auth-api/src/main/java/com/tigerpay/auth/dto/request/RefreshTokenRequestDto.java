package com.tigerpay.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class RefreshTokenRequestDto {

    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;
}
