package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.TransferRequestDto;
import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.enums.TransferCode;
import com.tigerpay.payment.model.TransferModel;
import com.tigerpay.payment.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final PaymentAccountService paymentAccountService;
    private final TransferRepository transferRepository;

    @Override
    public void transfer(final TransferRequestDto transferRequestDto) {
        val currentAccount = paymentAccountService.getCurrentUserPaymentAccounts()
                .stream()
                .filter(account -> account.ledger().equals(transferRequestDto.getLedger()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown ledger %s".formatted(transferRequestDto.getLedger())));

        val transferModel = new TransferModel(
                transferRequestDto.getReceiverId(),
                currentAccount.id(),
                transferRequestDto.getLedger(),
                Objects.nonNull(transferRequestDto.getCode()) ?
                        transferRequestDto.getCode() :
                        TransferCode.TRANSFER,
                transferRequestDto.getAmount()
        );

        transferRepository.transfer(transferModel);
    }

    @Override
    public List<TransferResponseDto> getAllByLedger(final AccountLedger ledger) {
        val currentAccount = paymentAccountService.getCurrentUserPaymentAccounts()
                .stream()
                .filter(account -> account.ledger().equals(ledger))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown ledger %s".formatted(ledger)));

        return transferRepository.findAllById(currentAccount.id());
    }
}
