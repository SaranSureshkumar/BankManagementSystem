package com.bms.bankmanagementsystem.service;

import com.bms.bankmanagementsystem.model.response.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public interface SavingsAccountService {
    ResponseModel createSavingsAccount(String customerKey);
}
