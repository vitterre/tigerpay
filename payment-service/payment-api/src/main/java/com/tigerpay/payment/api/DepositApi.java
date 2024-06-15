package com.tigerpay.payment.api;

import com.tigerpay.payment.dto.request.DepositRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Deposit money")
@RequestMapping("/api/v1/payments/deposits")
public interface DepositApi {

    @Operation(summary = "Deposit money to your account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully deposited money",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission",
                    content = @Content()
            )
    })
    @PostMapping
    void deposit(final @Valid @RequestBody DepositRequestDto depositRequestDto);
}
