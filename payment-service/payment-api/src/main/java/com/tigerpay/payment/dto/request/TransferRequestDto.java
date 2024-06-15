package com.tigerpay.payment.dto.request;

import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.enums.TransferCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class TransferRequestDto {

    @NotNull(message = "Receiver ID must not be null")
    @Min(value = 0)
    private BigInteger receiverId;

    @NotNull(message = "Ledger must not be null")
    private AccountLedger ledger;

    @NotNull(message = "Code must not be null")
    private TransferCode code;

    @NotNull(message = "Amount must not be null")
    @Min(value = 0)
    private BigDecimal amount;
}
