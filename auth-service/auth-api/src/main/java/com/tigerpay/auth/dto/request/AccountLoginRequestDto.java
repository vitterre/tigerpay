package com.tigerpay.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AccountLoginRequestDto {

    @NotBlank(message = "Subject must not be blank")
    @Pattern(
            regexp = "EMAIL_ADDRESS|PHONE_NUMBER",
            message = "Subject must be EMAIL_ADDRESS or PHONE_NUMBER"
    )
    private String subject;

    @NotBlank(message = "Login must not be blank")
    private String login;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
