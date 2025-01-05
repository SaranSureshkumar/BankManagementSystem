package com.bms.bankmanagementsystem.controller;

import com.bms.bankmanagementsystem.middleware.CustomerMiddleware;
import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.request.UpdateCustomer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer", name = "customer Controller")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerMiddleware customerMiddleWare;

    @PostMapping(value = "/registerCustomer", name = "Register/Create customer")
    public ResponseModel createUser(@RequestBody Customer customer){
        log.info("CUSTOMER CONTROLLER - CREATE CUSTOMER");
        return customerMiddleWare.createCustomer(customer);
    }

    @GetMapping(value = "/getCustomerDetail/{customerKey}", name = "Get customer details")
    public ResponseModel getCustomerDetail(@PathVariable String customerKey, @RequestHeader("Authorization") String jwtToken){
        log.info("CUSTOMER CONTROLLER - GET CUSTOMER DETAIL");
        return customerMiddleWare.getCustomerDetail(customerKey, jwtToken);
    }

    @PostMapping(value = "/updateCustomer", name = "Update customer details")
    public ResponseModel updateDetail(@RequestBody UpdateCustomer customer, @RequestHeader("Authorization") String jwtToken){
        log.info("CUSTOMER CONTROLLER - UPDATE CUSTOMER DETAIL");
        return customerMiddleWare.updateCustomerDetail(customer, jwtToken);
    }
}
