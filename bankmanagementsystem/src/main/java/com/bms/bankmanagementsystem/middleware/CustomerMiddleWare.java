package com.bms.bankmanagementsystem.middleware;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.request.UpdateCustomer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.service.CustomerService;
import com.bms.bankmanagementsystem.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerMiddleWare {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    public ResponseModel createCustomer(Customer customer){
        log.info("CUSTOMER MIDDLEWARE - CREATE CUSTOMER");
        return customerService.createCustomer(customer);
    }

    public ResponseModel getCustomerDetail(String customerKey, String jwtToken) {
        log.info("CUSTOMER MIDDLEWARE - GET CUSTOMER DETAIL");

        try{
            if(jwtService.verifyJwt(jwtToken).getStatusCode() == 200){
                return customerService.getCustomerDetails(customerKey);
            }else{
                return jwtService.verifyJwt(jwtToken);
            }
        }catch (Exception e){
            return new ResponseModel<>();
        }
    }

    public ResponseModel updateCustomerDetail(UpdateCustomer customer, String jwtToken) {
        log.info("CUSTOMER MIDDLEWARE - UPDATE CUSTOMER DETAIL");

        try{
            if(jwtService.verifyJwt(jwtToken).getStatusCode() == 200){
                return customerService.updateCustomerDetail(customer);
            }else{
                return jwtService.verifyJwt(jwtToken);
            }
        }catch (Exception e){
            return new ResponseModel<>();
        }
    }
}
