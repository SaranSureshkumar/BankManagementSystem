package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByPhoneNumber(String phoneNumber);

    Customer findByEmail(String email);

    Customer findByCustomerKey(String customerKey);
}
