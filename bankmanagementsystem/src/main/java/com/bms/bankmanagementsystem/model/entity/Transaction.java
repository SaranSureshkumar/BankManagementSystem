package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "savings_account_id", nullable = false)
    private SavingsAccount savingsAccount;

    private String transactionType;

    private String transactionFrom;

    private String transactionTo;

    private Double transactionAmount;

    private Double balanceAfterTransaction;

    private LocalDateTime transactionDateTime = LocalDateTime.now();
}