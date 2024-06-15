package com.tigerpay.payment.dto.request;

import com.tigerpay.payment.enums.AccountLedger;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequestDto {

    @NotNull(message = "Amount must not be null")
    @Min(value = 0)
    private BigDecimal amount;

    @NotNull(message = "Ledger must not be null")
    private AccountLedger ledger;
}
