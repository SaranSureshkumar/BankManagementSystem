package com.bms.bankmanagementsystem.middleware;

import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.service.JwtService;
import com.bms.bankmanagementsystem.service.SavingsAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SavingsAccountMiddleware {

    @Autowired
    private SavingsAccountService savingsAccountService;

    @Autowired
    private JwtService jwtService;

    public ResponseModel createSavingsAccount(String customerKey, String jwtToken) {
        log.info("SAVINGS ACCOUNT MIDDLEWARE - CREATE SAVINGS ACCOUNT");

        try{
            if(jwtService.verifyJwt(jwtToken).getStatusCode() == 200){
                return savingsAccountService.createSavingsAccount(customerKey);
            }else{
                return jwtService.verifyJwt(jwtToken);
            }
        }catch (Exception e){
            return new ResponseModel<>();
        }
    }
}
