package com.nerdbank.wallet.controllers;

import com.nerdbank.wallet.dtos.DepositWithdrawRequestDto;
import com.nerdbank.wallet.dtos.TransferRequestDto;
import com.nerdbank.wallet.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity getAll(Pageable pageable) {
        return ResponseEntity.ok(this.walletService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable UUID id) {
        var result = this.walletService.findById(id);
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity deposit(@PathVariable UUID id, @Validated @RequestBody DepositWithdrawRequestDto request) {
        return ResponseEntity.ok(this.walletService.deposit(id, request));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity withdraw(@PathVariable UUID id, @Validated @RequestBody DepositWithdrawRequestDto request) {
        return ResponseEntity.ok(this.walletService.withdraw(id, request));
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity transfer(@PathVariable UUID id, @Validated @RequestBody TransferRequestDto request) {
        return ResponseEntity.ok(this.walletService.transfer(id, request));
    }

}
