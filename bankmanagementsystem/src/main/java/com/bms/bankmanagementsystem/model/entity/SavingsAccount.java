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
    private Integer savingsAccountId;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", unique = true, nullable = false)
    private Customer customer;

    @Column(unique = true)
    private String accountNumber;

    private Double accountBalance = (double) 0;

    private Double minimumBalance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

}
