package com.tigerpay.payment.api;

import com.tigerpay.payment.dto.request.TransferRequestDto;
import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.enums.AccountLedger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transfer money")
@RequestMapping("/api/v1/payments/transfers")
public interface TransferApi {

    @Operation(summary = "Transfer money from one account to another")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully transferred money",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to view current payment account",
                    content = @Content()
            )
    })
    @PostMapping
    void transfer(final @Valid @RequestBody TransferRequestDto transferRequestDto);

    @Operation(summary = "Getting all user's transactions by ledger")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all transactions",
                    content = @Content(
                            schema = @Schema(implementation = TransferResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to view transactions",
                    content = @Content()
            )
    })
    @GetMapping
    List<TransferResponseDto> getAllByLedger(final @RequestParam("ledger") AccountLedger ledger);
}
