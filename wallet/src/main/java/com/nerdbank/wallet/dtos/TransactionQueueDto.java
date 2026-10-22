package com.nerdbank.wallet.dtos;

import com.nerdbank.wallet.domain.enums.TransactionTypeEnum;

import java.util.UUID;

public record TransactionQueueDto(
    UUID walletOriginId,

    UUID walletDestinyId,

    TransactionTypeEnum type,

    double amount
) { }
