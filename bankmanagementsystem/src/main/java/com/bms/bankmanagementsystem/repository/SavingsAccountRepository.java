package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {
}
