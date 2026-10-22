package com.nerdbank.transactions.services;

import com.nerdbank.transactions.domain.entities.Transaction;
import com.nerdbank.transactions.domain.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> findAll(Pageable pageable) {
        return this.transactionRepository.findAll(pageable);
    }

}
