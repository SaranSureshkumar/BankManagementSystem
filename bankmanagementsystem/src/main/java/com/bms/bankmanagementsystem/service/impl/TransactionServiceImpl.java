package com.bms.bankmanagementsystem.service.impl;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.entity.SavingsAccount;
import com.bms.bankmanagementsystem.model.entity.Transaction;
import com.bms.bankmanagementsystem.model.request.CreateTransaction;
import com.bms.bankmanagementsystem.model.request.GetTransactionDetails;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.repository.CustomerRepository;
import com.bms.bankmanagementsystem.repository.SavingsAccountRepository;
import com.bms.bankmanagementsystem.repository.TransactionRepository;
import com.bms.bankmanagementsystem.service.TransactionService;
import com.bms.bankmanagementsystem.utility.helper.ResponseStatusEnum;
import com.bms.bankmanagementsystem.utility.helper.TransactionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseModel createTransaction(CreateTransaction transactionData) {
        log.info("TRANSACTION SERVICE - CREATE TRANSACTION");

        ResponseModel responseModel = new ResponseModel();

        log.info("TRANSACTION DATA VALUE IS : {}", transactionData);

        try{
            if(transactionData != null && transactionData.getAmount() > 0 &&
                    !transactionData.getCustomerKey().isBlank() &&
                    !transactionData.getTransactionType().toString().isBlank()
            ){

                Customer existingCustomer = customerRepository.findByCustomerKey(transactionData.getCustomerKey());

                if(existingCustomer != null){

                    List<SavingsAccount> existingSavingsAccount = savingsAccountRepository.findByCustomer_CustomerId(existingCustomer.getCustomerId());

                    if(!existingSavingsAccount.isEmpty()){

                        AtomicBoolean foundTransactionAccount = new AtomicBoolean(false);

                        if(transactionData.getTransactionType().equals(TransactionTypeEnum.DEPOSIT)){
                            existingSavingsAccount.forEach(
                                    (account) -> {
                                        if(account.getAccountNumber().equals(transactionData.getTransactionTo()))
                                        {
                                            foundTransactionAccount.set(true);

                                            Transaction transaction =  new Transaction();

                                            transaction.setTransactionAmount(transactionData.getAmount());
                                            transaction.setTransactionType(String.valueOf(transactionData.getTransactionType()));
                                            transaction.setTransactionFrom("CASH");
                                            transaction.setTransactionTo(account.getAccountNumber());
                                            transaction.setBalanceAfterTransaction(transactionData.getAmount() + account.getAccountBalance());
                                            transaction.setSavingsAccount(account);

                                            transactionRepository.save(transaction);

                                            //Update Savings account balance amount

                                            account.setAccountBalance(transactionData.getAmount()+ account.getAccountBalance());

                                            savingsAccountRepository.save(account);

                                            // Return success response model

                                            responseModel.setData("Transaction Deposit successful");
                                            responseModel.setMessage(ResponseStatusEnum.Success);
                                            responseModel.setStatusCode(HttpStatus.OK.value());
                                        }
                                        //Else part is handled as a flag
                                    });
                        }
                        else if (transactionData.getTransactionType().equals(TransactionTypeEnum.WITHDRAWAL)) {
                            existingSavingsAccount.forEach(
                                    (account) -> {
                                        if(account.getAccountNumber().equals(transactionData.getTransactionFrom()))
                                        {
                                            foundTransactionAccount.set(true);

                                            if (transactionData.getAmount() <= account.getAccountBalance()) {

                                                Transaction transaction = new Transaction();

                                                transaction.setTransactionAmount(transactionData.getAmount());
                                                transaction.setTransactionType(String.valueOf(transactionData.getTransactionType()));
                                                transaction.setTransactionFrom(account.getAccountNumber());
                                                transaction.setTransactionTo("CASH");
                                                transaction.setBalanceAfterTransaction(account.getAccountBalance() - transactionData.getAmount());
                                                transaction.setSavingsAccount(account);

                                                transactionRepository.save(transaction);

                                                // Update Savings account Balance

                                                account.setAccountBalance(account.getAccountBalance() - transactionData.getAmount());

                                                savingsAccountRepository.save(account);

                                                //Return success response model

                                                responseModel.setData("Transaction WithDrawal successful");
                                                responseModel.setMessage(ResponseStatusEnum.Success);
                                                responseModel.setStatusCode(HttpStatus.OK.value());
                                            } else {
                                                responseModel.setData("Transaction cannot be completed due to in sufficient balance.");
                                                responseModel.setMessage(ResponseStatusEnum.Failure);
                                                responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                                            }
                                        }
                                        //Else part is handled as a flag
                                    });
                        } else{
                            responseModel.setData("Transaction type invalid");
                            responseModel.setMessage(ResponseStatusEnum.Failure);
                            responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                        }

                        if(!foundTransactionAccount.get()){
                            responseModel.setData("Transaction account not found for the user.");
                            responseModel.setMessage(ResponseStatusEnum.Failure);
                            responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                        }


                    }else{
                        responseModel.setData("Savings account not found for customer.");
                        responseModel.setMessage(ResponseStatusEnum.Failure);
                        responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    }

                }else{
                    responseModel.setData("Customer not found for the customer key.");
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                }
            }
            else{
                if(transactionData == null){
                    responseModel.setData("Transaction data is null");
                }
                else if(transactionData.getAmount() <=0){
                    responseModel.setData("Transaction amount is less than or equal to 0.");
                }
                else if(transactionData.getCustomerKey().isBlank()){
                    responseModel.setData("Transaction customer key value is empty/blank.");
                }
                else if(transactionData.getTransactionType().toString().isBlank()){
                    responseModel.setData("Transaction is empty/blank.");
                }
                else{
                    responseModel.setData("Contact tech support");
                }

                responseModel.setMessage(ResponseStatusEnum.Failure);
                responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
            }
        }catch(Exception e){
            log.error("ERROR OCCURRED : "+ e.getMessage());
        }

        return responseModel;
    }

    @Override
    public ResponseModel getCustomerTransaction(GetTransactionDetails getTransactionDetails) {
        log.info("TRANSACTION SERVICE - GET CUSTOMER TRANSACTION : {}", getTransactionDetails);

        ResponseModel responseModel = new ResponseModel<>();

        try{
            if(getTransactionDetails != null && !getTransactionDetails.getSavingsAccountNumber().isBlank()){
                SavingsAccount savingsAccount = savingsAccountRepository.findByAccountNumber(getTransactionDetails.getSavingsAccountNumber());

                log.info("SAVINGS ACCOUNT INFO : {}", savingsAccount);

                if(savingsAccount!=null){

                    Pageable pageable = PageRequest.of(
                            getTransactionDetails.getPageNumber(), getTransactionDetails.getPageSize(), Sort.by("transactionId").descending());

                    if(!getTransactionDetails.getDateFrom().isBlank() && !getTransactionDetails.getDateTo().isBlank()){


                        if(!getTransactionDetails.getTransactionType().isBlank()){
                            log.info("IN TRANSACTION DATA TYPE WITH DATE");

                            Page<Transaction> transactionList = transactionRepository.findBySavingsAccount_SavingsAccountIdAndTransactionTypeAndTransactionDateTimeGreaterThanAndTransactionDateTimeLessThan(
                                    savingsAccount.getSavingsAccountId(), getTransactionDetails.getTransactionType(),
                                    LocalDate.parse(getTransactionDetails.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                                    LocalDate.parse(getTransactionDetails.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                                    pageable);

                            responseModel.setData(transactionList);
                        }
                        else{
                            log.info("IN TRANSACTION DATA WITH DATE");
                            Page<Transaction> transactionList = transactionRepository.findBySavingsAccount_SavingsAccountIdAndTransactionDateTimeGreaterThanAndTransactionDateTimeLessThan(
                                    savingsAccount.getSavingsAccountId(),
                                    LocalDate.parse(getTransactionDetails.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                                    LocalDate.parse(getTransactionDetails.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                                    pageable);

                            responseModel.setData(transactionList);
                        }
                    }
                    else if(!getTransactionDetails.getTransactionType().isBlank()){
                        log.info("IN TRANSACTION DATA OF TYPE");

                        Page<Transaction> transactionList = transactionRepository.findBySavingsAccount_SavingsAccountIdAndTransactionType(
                                savingsAccount.getSavingsAccountId(), getTransactionDetails.getTransactionType(), pageable);

                        responseModel.setData(transactionList);
                    }
                    else{
                        log.info("IN ONLY TRANSACTION DATA");
                        Page<Transaction> transactionList = transactionRepository.findBySavingsAccount_SavingsAccountId(savingsAccount.getSavingsAccountId(), pageable);

                        responseModel.setData(transactionList);
                    }

                    responseModel.setMessage(ResponseStatusEnum.Success);
                    responseModel.setStatusCode(HttpStatus.OK.value());
                }
                else{
                    responseModel.setData("Savings account not found.");
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                }
            }
            else{
                if(getTransactionDetails == null){
                    responseModel.setData("Transaction details of request body is null.");
                }
                else {
                    responseModel.setData("Customer key value is empty/blank.");
                }
                responseModel.setMessage(ResponseStatusEnum.Failure);
                responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
            }
        }catch(Exception e){
            log.error("ERROR OCCURRED - ", e);
        }

        return responseModel;
    }
}
