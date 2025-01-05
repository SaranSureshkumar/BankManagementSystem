package com.bms.bankmanagementsystem.controller;


import com.bms.bankmanagementsystem.middleware.SavingsAccountMiddleware;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savingAccount")
@Slf4j
public class SavingAccountController {

    @Autowired
    private SavingsAccountMiddleware savingsAccountMiddleware;

    @PostMapping(value = "/create/{customerKey}", name = "Create savings account")
    public ResponseModel createSavingAccount(@PathVariable String customerKey, @RequestHeader("Authorization") String jwtToken){
        log.info("SAVINGS ACCOUNT CONTOLLER - CREATE SAVINGS ACCOUNT");
        return savingsAccountMiddleware.createSavingsAccount(customerKey, jwtToken);
    }
}
