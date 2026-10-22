package com.nerdbank.wallet.services;

import com.nerdbank.wallet.config.BadRequestException;
import com.nerdbank.wallet.domain.entities.Wallet;
import com.nerdbank.wallet.domain.repositories.WalletRepository;
import com.nerdbank.wallet.dtos.DepositWithdrawRequestDto;
import com.nerdbank.wallet.dtos.TransferRequestDto;
import com.nerdbank.wallet.dtos.WalletRequestDto;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Page<Wallet> findAll(Pageable pageable) {
        return this.walletRepository.findAll(pageable);
    }

    public Optional<Wallet> findById(UUID id) {
        return this.walletRepository.findById(id);
    }

    public Wallet create(WalletRequestDto walletRequestDto) {
        var wallet = new Wallet(
                null,
                walletRequestDto.balance(),
                LocalDateTime.now(),
                LocalDateTime.now());
        this.walletRepository.save(wallet);
        return wallet;
    }

    public Wallet deposit(UUID id, DepositWithdrawRequestDto depositWithdrawRequestDto) {
        var wallet = this.findById(id);
        if(wallet.isEmpty()) {
            throw new BadRequestException("Wallet not found");
        }

        if(depositWithdrawRequestDto.amount() <= 0) {
            throw new BadRequestException("The value must be greater than 0");
        }

        wallet.get().setBalance(wallet.get().getBalance() + depositWithdrawRequestDto.amount());
        wallet.get().setUpdatedAt(LocalDateTime.now());

        this.walletRepository.save(wallet.get());
        return wallet.get();
    }

    public Wallet withdraw(UUID id, DepositWithdrawRequestDto depositWithdrawRequestDto) {
        var wallet = this.findById(id);
        if(wallet.isEmpty()) {
            throw new BadRequestException("Wallet not found");
        }

        if(depositWithdrawRequestDto.amount() <= 0) {
            throw new BadRequestException("The value must be greater than 0");
        }

        if(depositWithdrawRequestDto.amount() > wallet.get().getBalance()) {
            throw new BadRequestException("The amount is greater than the balance");
        }

        wallet.get().setBalance(wallet.get().getBalance() - depositWithdrawRequestDto.amount());
        wallet.get().setUpdatedAt(LocalDateTime.now());

        this.walletRepository.save(wallet.get());
        return wallet.get();
    }

    public Wallet transfer(UUID id, TransferRequestDto transferRequestDto) {
        if(transferRequestDto.walletDestinyId().toString().equals(id.toString())) {
            throw new BadRequestException("the source wallet and the destiny wallet are the same");
        }

        var walletOrigin = this.findById(id);
        var walletDestiny = this.findById(transferRequestDto.walletDestinyId());

        if(walletOrigin.isEmpty()) {
            throw new BadRequestException("Source wallet not found");
        }
        if(walletDestiny.isEmpty()) {
            throw new BadRequestException("Destiny wallet not found");
        }


        if(transferRequestDto.amount() <= 0) {
            throw new BadRequestException("The value must be greater than 0");
        }
        if(transferRequestDto.amount() > walletOrigin.get().getBalance()) {
            throw new BadRequestException("The amount is greater than the balance os source wallet");
        }

        // WITHDRAW
        walletOrigin.get().setBalance(walletOrigin.get().getBalance() - transferRequestDto.amount());
        walletOrigin.get().setUpdatedAt(LocalDateTime.now());
        this.walletRepository.save(walletOrigin.get());

        // DEPOSIT
        walletDestiny.get().setBalance(walletDestiny.get().getBalance() + transferRequestDto.amount());
        walletDestiny.get().setUpdatedAt(LocalDateTime.now());
        this.walletRepository.save(walletDestiny.get());

        return walletOrigin.get();
    }

    private void kafkaProducer(String message) {

    }

}
