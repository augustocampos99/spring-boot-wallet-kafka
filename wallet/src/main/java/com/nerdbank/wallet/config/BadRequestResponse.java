package com.nerdbank.wallet.config;

public record BadRequestResponse(
        int status,
        String message
) { }
