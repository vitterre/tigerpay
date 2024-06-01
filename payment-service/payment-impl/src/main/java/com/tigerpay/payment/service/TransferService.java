package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.TransferRequestDto;
import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.enums.AccountLedger;

import java.util.List;

public interface TransferService {

    void transfer(final TransferRequestDto transferRequestDto);
    List<TransferResponseDto> getAllByLedger(final AccountLedger ledger);
}
