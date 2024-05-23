package com.tigerpay.auth.dto.request;

import jakarta.validation.constraints.Email;
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
public final class AccountRegisterRequestDto {

    @NotBlank(message = "First name must not be blank")
    @Pattern(
            regexp = "[a-zA-Z]{1,50}",
            message = "First name must contain only latin characters and have length from 1 up to 50 characters"
    )
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Pattern(
            regexp = "[a-zA-Z]{1,50}",
            message = "Last name must contain only latin characters and have length from 1 up to 50 characters"
    )
    private String lastName;

    @Pattern(
            regexp = "[a-zA-Z]{1,50}",
            message = "Middle name must contain only latin characters and have length from 1 up to 50 characters"
    )
    private String middleName;

    @Email(message = "Email address is not valid")
    private String emailAddress;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "(^$|[+][0-9]{11})",
            message = "Phone number is not valid"
    )
    private String phoneNumber;

    @NotBlank(message = "Password must not be balnk")
    private String password;
}
