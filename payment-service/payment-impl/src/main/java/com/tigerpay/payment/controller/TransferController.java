package com.tigerpay.payment.controller;

import com.tigerpay.payment.api.TransferApi;
import com.tigerpay.payment.dto.request.TransferRequestDto;
import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferController implements TransferApi {

    private final TransferService transferService;

    @Override
    public void transfer(final TransferRequestDto transferRequestDto) {
        transferService.transfer(transferRequestDto);
    }

    @Override
    public List<TransferResponseDto> getAllByLedger(final AccountLedger ledger) {
        return transferService.getAllByLedger(ledger);
    }
}
