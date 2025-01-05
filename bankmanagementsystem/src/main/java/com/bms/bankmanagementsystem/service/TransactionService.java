package com.bms.bankmanagementsystem.service;

import com.bms.bankmanagementsystem.model.request.CreateTransaction;
import com.bms.bankmanagementsystem.model.request.GetTransactionDetails;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public interface TransactionService {
    ResponseModel createTransaction(CreateTransaction transactionData);

    ResponseModel getCustomerTransaction(GetTransactionDetails getTransactionDetails);
}
