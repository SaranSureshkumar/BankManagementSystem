package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {
}
