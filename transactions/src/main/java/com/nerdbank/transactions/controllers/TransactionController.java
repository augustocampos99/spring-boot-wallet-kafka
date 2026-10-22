package com.nerdbank.transactions.controllers;

import com.nerdbank.transactions.services.TransactionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping({"","/"})
    public ResponseEntity gitAll(Pageable pageable) {
        return ResponseEntity.ok(this.transactionService.findAll(pageable));
    }

}
