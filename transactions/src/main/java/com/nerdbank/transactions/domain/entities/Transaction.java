package com.nerdbank.transactions.domain.entities;

import com.nerdbank.transactions.domain.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "wallet_origin_id")
    private UUID walletOriginId;

    @Column(name = "wallet_destiny_id")
    private UUID walletDestinyId;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;

    private double amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
