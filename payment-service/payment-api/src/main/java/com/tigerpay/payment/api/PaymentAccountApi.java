package com.tigerpay.payment.api;


import com.tigerpay.payment.dto.response.PaymentAccountResponseDto;
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

import java.util.List;

@Tag(name = "Payment accounts")
@RequestMapping("/api/v1/payments/accounts")
public interface PaymentAccountApi {

    @Operation(summary = "Get current payment account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment accounts found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentAccountResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to view current payment account",
                    content = @Content()
            )
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<PaymentAccountResponseDto> getCurrentUserPaymentAccounts();
}
