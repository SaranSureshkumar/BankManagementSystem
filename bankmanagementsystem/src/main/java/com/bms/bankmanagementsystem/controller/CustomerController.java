package com.bms.bankmanagementsystem.controller;

import com.bms.bankmanagementsystem.middleware.CustomerMiddleWare;
import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer", name = "customer Controller")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerMiddleWare customerMiddleWare;

    @PostMapping(value = "/registerCustomer", name = "Register User")
    public ResponseModel createUser(@RequestBody Customer customer){
        log.info("CUSTOMER CONTROLLER - Create User");
        return customerMiddleWare.createCustomer(customer);
    }

    @GetMapping(value = "/getCustomerDetail/{customerKey}", name = "Get Customer Details")
    public ResponseModel getCustomerDetail(@PathVariable String customerKey, @RequestHeader("Authorization") String jwtToken){
        log.info("CUSTOMER CONTROLLER - Get customer detail");
        return customerMiddleWare.getCustomerDetail(customerKey, jwtToken);
    }
}
