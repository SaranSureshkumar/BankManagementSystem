package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
