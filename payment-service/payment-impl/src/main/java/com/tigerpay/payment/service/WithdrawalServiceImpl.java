package com.tigerpay.payment.service;

import com.tigerpay.payment.dto.request.WithdrawalRequestDto;
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
public class WithdrawalServiceImpl implements WithdrawalService {

    private final PaymentAccountService paymentAccountService;
    private final TransferRepository transferRepository;
    private final KafkaTemplate<UUID, TransferEvent> kafkaTransferEventTemplate;

    @Override
    public void withdrawal(final WithdrawalRequestDto withdrawalRequestDto) {
        val currentAccount = paymentAccountService.getCurrentUserPaymentAccounts()
                .stream()
                .filter(account -> account.ledger().equals(withdrawalRequestDto.getLedger()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown ledger %s".formatted(withdrawalRequestDto.getLedger())));

        val transferModel = new TransferModel(
                BigInteger.valueOf(withdrawalRequestDto.getLedger().getLedger() * 10),
                currentAccount.id(),
                withdrawalRequestDto.getLedger(),
                TransferCode.WITHDRAWAL,
                withdrawalRequestDto.getAmount()
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
