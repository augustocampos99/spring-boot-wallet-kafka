package com.nerdbank.wallet.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DepositWithdrawRequestDto(

        @NotNull(message = "amount is required")
        @Min(value = 0, message = "the amount cannot be negative")
        double amount
) { }
