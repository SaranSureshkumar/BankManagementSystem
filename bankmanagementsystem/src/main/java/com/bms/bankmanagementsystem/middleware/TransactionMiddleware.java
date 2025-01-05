package com.bms.bankmanagementsystem.middleware;

import com.bms.bankmanagementsystem.model.request.CreateTransaction;
import com.bms.bankmanagementsystem.model.request.GetTransactionDetails;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.service.JwtService;
import com.bms.bankmanagementsystem.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionMiddleware {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TransactionService transactionService;

    public ResponseModel createTransaction(CreateTransaction transactionData, String jwtToken){
        log.info("TRANSACTION MIDDLEWARE - CREATE TRANSACTION");

        try{
            if(jwtService.verifyJwt(jwtToken).getStatusCode() == 200){
                return transactionService.createTransaction(transactionData);
            }else{
                return jwtService.verifyJwt(jwtToken);
            }
        }catch (Exception e){
            return new ResponseModel<>();
        }
    }

    public ResponseModel getCustomerTransaction(GetTransactionDetails getTransactionDetails, String jwtToken) {

        log.info("TRANSACTION MIDDLEWARE - GET CUSTOMER TRANSACTION");

        try{
            if(jwtService.verifyJwt(jwtToken).getStatusCode() == 200){
                return transactionService.getCustomerTransaction(getTransactionDetails);
            }else{
                return jwtService.verifyJwt(jwtToken);
            }
        }catch (Exception e){
            return new ResponseModel<>();
        }
    }
}
