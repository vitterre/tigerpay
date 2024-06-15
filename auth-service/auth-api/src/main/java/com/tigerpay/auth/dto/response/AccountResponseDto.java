package com.tigerpay.auth.dto.response;

import com.tigerpay.auth.dto.enums.Role;

import java.util.UUID;

public record AccountResponseDto(UUID uuid,
                                 String firstName,
                                 String lastName,
                                 String middleName,
                                 String emailAddress,
                                 String phoneNumber,
                                 Role role) {
}
