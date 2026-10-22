package com.nerdbank.wallet.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TransferRequestDto(

        @NotNull(message = "amount is required")
        @Min(value = 0, message = "the amount cannot be negative")
        double amount,

        @NotNull(message = "destinyId is required")
        UUID walletDestinyId

) { }
