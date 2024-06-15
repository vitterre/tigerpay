package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.DepositRequestDto;
import com.tigerpay.payment.dto.request.TransferRequestDto;
import com.tigerpay.payment.enums.TransferCode;
import com.tigerpay.payment.event.TransferEvent;
import com.tigerpay.payment.model.TransferModel;
import com.tigerpay.payment.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final PaymentAccountService paymentAccountService;
    private final TransferRepository transferRepository;
    private final KafkaTemplate<UUID, TransferEvent> kafkaTransferEventTemplate;

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

        val transferId = transferRepository.transfer(transferModel);

        val senderId = transferModel.creditAccountId();
        val receiverId = transferModel.debitAccountId();

        val senderAccount = paymentAccountService.getAccountById(senderId);
        val receiverAccount = paymentAccountService.getAccountById(receiverId);

        val transferEvent = new TransferEvent(
                transferId,
                receiverId,
                senderId,
                receiverAccount.profileUuid(),
                senderAccount.profileUuid(),
                transferModel.amount(),
                transferModel.ledger(),
                transferModel.code()
        );

        kafkaTransferEventTemplate.send(
                "sink-transfer-events",
                UUID.randomUUID(),
                transferEvent
        );
    }
}
