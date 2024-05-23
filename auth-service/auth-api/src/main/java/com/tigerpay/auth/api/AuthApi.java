package com.tigerpay.auth.api;

import com.tigerpay.auth.dto.request.AccountLoginRequestDto;
import com.tigerpay.auth.dto.request.AccountRegisterRequestDto;
import com.tigerpay.auth.dto.request.RefreshTokenRequestDto;
import com.tigerpay.auth.dto.response.TokenCoupleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Authorization")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @Operation(summary = "Registration")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Account successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenCoupleResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Provided data for registration are not valid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Account with provided email address or phone number already exists",
                    content = @Content
            )
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    TokenCoupleResponseDto register(final @Valid @RequestBody AccountRegisterRequestDto accountRegisterRequestDto);

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Successfully logged in",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenCoupleResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email address or phone number fields are not valid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Wrong credentials provided",
                    content = @Content
            )
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    TokenCoupleResponseDto login(final @Valid @RequestBody AccountLoginRequestDto accountLoginRequestDto);


    @Operation(summary = "Refreshing token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Successfully refreshed token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenCoupleResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Provided refresh token is not valid",
                    content = @Content
            )
    })
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.ACCEPTED)
    TokenCoupleResponseDto refresh(final @Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto);
}
