package com.bms.bankmanagementsystem.service.impl;

import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.repository.CustomerRepository;
import com.bms.bankmanagementsystem.service.CustomerService;
import com.bms.bankmanagementsystem.utility.contsants.Constants;
import com.bms.bankmanagementsystem.utility.helper.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Constants constants;

    @Override
    public ResponseModel createCustomer(Customer customer) {
        log.info("CUSTOMER SERVICE - CREATE CUSTOMER");

        ResponseModel responseModel  = new ResponseModel();

        if(customer != null && !customer.toString().isBlank()){
            Customer existingCustomerByEmail = customerRepository.findByEmail(customer.getEmail());
            Customer existingCustomerByPhoneNumber = customerRepository.findByPhoneNumber(customer.getPhoneNumber());

            if(
                    (existingCustomerByEmail == null || existingCustomerByEmail.toString().isBlank()) ||
                            (existingCustomerByPhoneNumber == null || existingCustomerByPhoneNumber.toString().isBlank())
            ){
                if(checkCustomerValid(customer)){
                    if(checkPasswordValid(customer.getPassword())){

                        customer.setCustomerKey(createRandomCharacters(constants.customerKeyLength));

                        customer.setCreatedAt(new Date().toString());
                        customer.setUpdatedAt(new Date().toString());

                        customerRepository.save(customer);

                        responseModel.setStatusCode(HttpStatus.OK.value());
                        responseModel.setMessage(ResponseStatusEnum.Success);
                        responseModel.setData("Customer saved successfully.");
                    }
                    else{
                        responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                        responseModel.setMessage(ResponseStatusEnum.Failure);
                        responseModel.setData("Customer password is not valid.");
                    }
                }
                else {
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setData("Customer data found in appropriate with null values.");
                }
            }
            else{
                if(!existingCustomerByEmail.toString().isBlank()){
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setData("Customer already exist for this E-Mail ID.");
                }
                else if(!existingCustomerByPhoneNumber.toString().isBlank()){
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setData("Customer already exist for this Phone Number.");
                }
                else{
                    responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    responseModel.setMessage(ResponseStatusEnum.Failure);
                    responseModel.setData("Contact Tech Support.");
                }
            }
        }
        else{
            responseModel.setData("Customer data is null");
            responseModel.setMessage(ResponseStatusEnum.Failure);
            responseModel.setStatusCode(HttpStatus.FOUND.value());
        }

        return responseModel;
    }

    @Override
    public ResponseModel getCustomerDetails(String customerKey) {
        log.info("CUSTOMER SERVICE - GET CUSTOMER DETAILS");

        ResponseModel responseModel = new ResponseModel<>();

        try{
            Customer customer = customerRepository.findByCustomerKey(customerKey);

            if(!customer.toString().isBlank()){
                responseModel.setStatusCode(HttpStatus.OK.value());
                responseModel.setMessage(ResponseStatusEnum.Success);
                responseModel.setData(customer);
            }else{
                responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseModel.setMessage(ResponseStatusEnum.Failure);
                responseModel.setData("Customer data not found.");
            }

        }catch (Exception e){
            responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseModel.setMessage(ResponseStatusEnum.Failure);
            responseModel.setData("ERROR OCCURED - "+e.getMessage());
        }

        return responseModel;
    }

    private boolean checkCustomerValid(Customer customer){
        log.info("CUSTOMER SERVICE - CHECK CUSTOMER VALID");

        if((customer.getFirstName() != null  && !customer.getFirstName().isBlank()) &&
            (customer.getLastName() != null && !customer.getLastName().isBlank()) &&
            (customer.getPhoneNumber() != null && !customer.getPhoneNumber().isBlank()) &&
            (customer.getEmail() != null && !customer.getEmail().isBlank() && customer.getEmail().contains("@") && customer.getEmail().contains(".com")) &&
            (customer.getAddress() != null && !customer.getAddress().isBlank()) &&
            (customer.getPostalCode() != null && !customer.getPostalCode().isBlank()) &&
            (customer.getCity() != null && !customer.getPostalCode().isBlank()) &&
            (customer.getCountry() != null && !customer.getCountry().isBlank()))
        {
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkPasswordValid(String customerPassword){
        log.info("CUSTOMER SERVICE - CHECK PASSWORD.");

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        if( customerPassword != null && customerPassword.matches(regex)){
            return true;
        }
        else{
            return false;
        }
    }

    private String createRandomCharacters(int length){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom RANDOM = new SecureRandom();

        return RANDOM.ints(length, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
