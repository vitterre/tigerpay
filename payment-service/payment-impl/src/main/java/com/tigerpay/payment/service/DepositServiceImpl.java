package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.DepositRequestDto;
import com.tigerpay.payment.enums.TransferCode;
import com.tigerpay.payment.model.TransferModel;
import com.tigerpay.payment.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final PaymentAccountService paymentAccountService;
    private final TransferRepository transferRepository;

    @Override
    public void deposit(final DepositRequestDto depositRequestDto) {
        val currentAccount = paymentAccountService.getCurrentUserPaymentAccounts()
                .stream()
                .filter(account -> account.ledger().equals(depositRequestDto.getLedger()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown ledger %s".formatted(depositRequestDto.getLedger())));

        val transferModel = new TransferModel(
                currentAccount.id(),
                BigInteger.valueOf(depositRequestDto.getLedger().getLedger() * 10),
                depositRequestDto.getLedger(),
                TransferCode.DEPOSIT,
                depositRequestDto.getAmount()
        );

        transferRepository.transfer(transferModel);
    }
}
