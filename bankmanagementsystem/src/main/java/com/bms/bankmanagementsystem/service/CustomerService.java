package com.bms.bankmanagementsystem.service;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public interface CustomerService {

    ResponseModel createCustomer(Customer customer);

    ResponseModel getCustomerDetails(String customerKey);
}
