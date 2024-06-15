package com.tigerpay.payment.repository;

import com.tigerpay.payment.dto.response.TransferResponseDto;
import com.tigerpay.payment.model.TransferModel;

import java.math.BigInteger;
import java.util.List;

public interface TransferRepository {

    BigInteger transfer(final TransferModel transferModel);
    List<TransferResponseDto> findAllById(final BigInteger id);
}
