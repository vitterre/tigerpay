package com.tigerpay.auth.api;

import com.tigerpay.auth.dto.response.AccountResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Accounts")
@RequestMapping("/api/v1/accounts")
public interface AccountApi {

    @Operation(summary = "Get current account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to view current account",
                    content = @Content()
            )
    })
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    AccountResponseDto getCurrentUserAccount();
}
