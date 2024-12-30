package com.bms.bankmanagementsystem.repository;

import com.bms.bankmanagementsystem.model.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByPhoneNumber(String phoneNumber);

    Customer findByEmail(String email);

    Customer findByPhoneNumberOrEmail(String phoneNumber, String email);
}
