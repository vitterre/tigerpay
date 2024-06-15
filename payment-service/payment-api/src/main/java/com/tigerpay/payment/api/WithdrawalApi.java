package com.tigerpay.payment.api;

import com.tigerpay.payment.dto.request.DepositRequestDto;
import com.tigerpay.payment.dto.request.WithdrawalRequestDto;
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
@RequestMapping("/api/v1/payments/withdrawals")
public interface WithdrawalApi {

    @Operation(summary = "Withdrawal money from your account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission",
                    content = @Content()
            )
    })
    @PostMapping
    void withdrawal(final @Valid @RequestBody WithdrawalRequestDto withdrawalRequestDto);
}
