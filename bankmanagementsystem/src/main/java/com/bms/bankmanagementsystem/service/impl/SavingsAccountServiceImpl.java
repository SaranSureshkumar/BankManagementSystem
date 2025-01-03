package com.bms.bankmanagementsystem.service.impl;

import com.bms.bankmanagementsystem.model.entity.AccountDetails;
import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.entity.SavingsAccount;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.repository.AccountDetailsRepository;
import com.bms.bankmanagementsystem.repository.CustomerRepository;
import com.bms.bankmanagementsystem.repository.SavingsAccountRepository;
import com.bms.bankmanagementsystem.service.SavingsAccountService;
import com.bms.bankmanagementsystem.utility.contsants.Constants;
import com.bms.bankmanagementsystem.utility.helper.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@Slf4j
public class SavingsAccountServiceImpl implements SavingsAccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Override
    public ResponseModel createSavingsAccount(String customerKey) {
        log.info("SAVINGS ACCOUNT SERVICE - CREATE SAVINGS ACCOUNT");

        ResponseModel responseModel = new ResponseModel<>();

        try {
            if(customerKey != null){
                Customer existingCustomer = customerRepository.findByCustomerKey(customerKey);

                if(existingCustomer != null){

                    AccountDetails exisitingAccountDetails = accountDetailsRepository.findById(existingCustomer.getCustomerId()).orElse(null);

                    if(exisitingAccountDetails != null){
                        exisitingAccountDetails.setSavingsAccount(true);
                        exisitingAccountDetails.setSavingsAccountCounts(exisitingAccountDetails.getSavingsAccountCounts() + 1 );

                        accountDetailsRepository.save(exisitingAccountDetails);
                    }
                    else {
                        AccountDetails accountDetails = new AccountDetails();

                        accountDetails.setCustomerId(existingCustomer.getCustomerId());
                        accountDetails.setSavingsAccount(true);
                        accountDetails.setSavingsAccountCounts(1);

                        accountDetailsRepository.save(accountDetails);
                    }

                    SavingsAccount savingsAccount = new SavingsAccount();

                    savingsAccount.setMinimumBalance(Constants.savingsAccountMinimumBalance);
                    savingsAccount.setAccountNumber(generateSavingsAccountNumber());
                    savingsAccount.setCreatedAt(LocalDateTime.now());
                    savingsAccount.setUpdatedAt(LocalDateTime.now());
                    savingsAccount.setCustomer(existingCustomer);

                    savingsAccountRepository.save(savingsAccount);

                    responseModel.setData("Savings account created for the customer.");
                    responseModel.setMessage(ResponseStatusEnum.Success);
                    responseModel.setStatusCode(HttpStatus.OK.value());

                }else{
                    responseModel.setData("Customer doesn't exist for the customer ID");
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setStatusCode(HttpStatus.FOUND.value());
                }
            }
            else{
                responseModel.setData("Customer ID is null");
                responseModel.setMessage(ResponseStatusEnum.Failure);
                responseModel.setStatusCode(HttpStatus.FOUND.value());
            }
        }catch (Exception e){
            log.error("ERROR OCCURRED : "+ e);
        }

        return responseModel;
    }

    private String generateSavingsAccountNumber(){
        String CHARACTERS = "0123456789";

        SecureRandom RANDOM = new SecureRandom();

        return RANDOM.ints(14, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
