package com.bms.bankmanagementsystem.middleware;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerMiddleWare {

    @Autowired
    private CustomerService customerService;

    public ResponseModel createCustomer(Customer customer){
        log.info("CUSTOMER MIDDLEWARE - CREATE CUSTOMER");
        return customerService.createCustomer(customer);
    }
}
