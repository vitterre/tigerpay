package com.tigerpay.payment.repository;

import com.tigerbeetle.AccountFilter;
import com.tigerbeetle.Client;
import com.tigerbeetle.TransferBatch;
import com.tigerbeetle.UInt128;
import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.enums.AccountLedger;
import com.tigerpay.payment.enums.TransferCode;
import com.tigerpay.payment.exception.AccountNotFoundServiceException;
import com.tigerpay.payment.exception.TransferBadRequestServiceException;
import com.tigerpay.payment.exception.TransferCreationInternalErrorServiceException;
import com.tigerpay.payment.model.TransferModel;
import com.tigerpay.payment.security.util.SecurityGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TransferTigerBeetleRepositoryImpl implements TransferRepository {

    private final Client tigerBeetleClient;

    @Override
    @SneakyThrows
    public BigInteger transfer(final TransferModel transferModel) {
        val generatedTransferId = SecurityGeneratorUtil.generateId();
        val transfers = new TransferBatch(1);

        transfers.add();
        transfers.setId(
                UInt128.asBytes(generatedTransferId)
        );
        transfers.setDebitAccountId(
                UInt128.asBytes(transferModel.debitAccountId())
        );
        transfers.setCreditAccountId(
                UInt128.asBytes(transferModel.creditAccountId())
        );
        transfers.setLedger(
                transferModel.ledger().getLedger()
        );
        transfers.setCode(
                transferModel.code().getCode()
        );
        transfers.setAmount(
                transferModel.amount()
                        .multiply(BigDecimal.valueOf(100))
                        .toBigIntegerExact()
        );
        transfers.setTimeout(0);

        val errors = tigerBeetleClient.createTransfers(transfers);

        while (errors.next()) {
            switch (errors.getResult()) {
                case ExceedsDebits -> throw new TransferBadRequestServiceException();
                case DebitAccountNotFound -> throw new AccountNotFoundServiceException(
                        transferModel.debitAccountId()
                );
                case CreditAccountNotFound -> throw new AccountNotFoundServiceException(
                        transferModel.creditAccountId()
                );
                default -> throw new TransferCreationInternalErrorServiceException();
            }
        }

        return generatedTransferId;
    }

    @SneakyThrows
    @Override
    public List<TransferResponseDto> findAllById(final BigInteger id) {
        val filter = new AccountFilter();
        filter.setAccountId(UInt128.asBytes(id));
        filter.setTimestampMin(0);
        filter.setTimestampMax(0);
        filter.setLimit(10);
        filter.setDebits(true);
        filter.setCredits(true);
        filter.setReversed(true);
        val transfers = tigerBeetleClient.getAccountTransfers(filter);

        val result = new ArrayList<TransferResponseDto>();

        while (transfers.next()) {
            result.add(
                    new TransferResponseDto(
                            UInt128.asBigInteger(transfers.getId()),
                            UInt128.asBigInteger(transfers.getDebitAccountId()),
                            UInt128.asBigInteger(transfers.getCreditAccountId()),
                            new BigDecimal(transfers.getAmount())
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY),
                            AccountLedger.getByLedger(transfers.getLedger()),
                            Objects.nonNull(TransferCode.getByCode(transfers.getCode())) ?
                                    TransferCode.getByCode(transfers.getCode()).name() :
                                    TransferCode.TRANSFERS.name(),
                            new Date(transfers.getTimestamp() / 1_000_000)
                    )
            );
        }

        return result;
    }
}
