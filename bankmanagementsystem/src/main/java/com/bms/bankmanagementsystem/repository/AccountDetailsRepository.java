package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {
}
