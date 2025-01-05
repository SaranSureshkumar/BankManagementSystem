package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findBySavingsAccount_SavingsAccountId(Integer savingsAccountId, Pageable pageable);

    Page<Transaction> findBySavingsAccount_SavingsAccountIdAndTransactionType(Integer savingsAccountId, String transactionType, Pageable pageable);

    Page<Transaction> findBySavingsAccount_SavingsAccountIdAndTransactionDateTimeGreaterThanAndTransactionDateTimeLessThan(Integer savingsAccountId, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Transaction> findBySavingsAccount_SavingsAccountIdAndTransactionTypeAndTransactionDateTimeGreaterThanAndTransactionDateTimeLessThan(
            Integer savingsAccountId, String transactionType, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
