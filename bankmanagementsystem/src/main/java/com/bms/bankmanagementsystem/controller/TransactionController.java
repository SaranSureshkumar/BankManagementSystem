package com.bms.bankmanagementsystem.controller;

import com.bms.bankmanagementsystem.middleware.TransactionMiddleware;
import com.bms.bankmanagementsystem.model.request.CreateTransaction;
import com.bms.bankmanagementsystem.model.request.GetTransactionDetails;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionMiddleware transactionMiddleware;

    @PostMapping(value = "/createTransaction", name = "Create a transaction (Deposit or Withdrawal)")
    public ResponseModel createTransaction(@RequestBody CreateTransaction transactionData, @RequestHeader("Authorization") String jwtToken){
        log.info("TRANSACTION CONTROLLER - CREATE TRANSACTION : {}", transactionData);
        return transactionMiddleware.createTransaction(transactionData, jwtToken);
    }

    @GetMapping(value = "/getTransaction", name = "GET CUSTOMER TRANSACTION")
    public  ResponseModel getCustomerTransaction(@RequestBody GetTransactionDetails getTransactionDetails, @RequestHeader("Authorization") String jwtToken){
        log.info("TRANSACTION CONTROLLER - GET CUSTOMER TRANSACTION");
        return transactionMiddleware.getCustomerTransaction(getTransactionDetails, jwtToken);
    }
}
