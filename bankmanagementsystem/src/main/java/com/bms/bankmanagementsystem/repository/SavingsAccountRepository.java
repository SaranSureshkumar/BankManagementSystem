package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {

    List<SavingsAccount> findByCustomer_CustomerId(int customerId);

    SavingsAccount findByAccountNumber(String accountNumber);
}
