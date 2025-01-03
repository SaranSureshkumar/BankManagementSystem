package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SavingsAccount")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savingAccountId;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true, nullable = false)
    private Customer customer;

    @Column(unique = true)
    private String accountNumber;

    private Double accountBalance;

    private Double minimumBalance;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
