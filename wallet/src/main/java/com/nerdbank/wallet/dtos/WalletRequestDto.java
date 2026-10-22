package com.nerdbank.wallet.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WalletRequestDto(

        @NotNull(message = "balance is required")
        @Min(value = 0, message = "the balance cannot be negative")
        double balance
) { }
