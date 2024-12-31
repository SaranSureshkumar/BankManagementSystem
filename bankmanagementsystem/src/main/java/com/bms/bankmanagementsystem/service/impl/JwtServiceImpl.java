package com.bms.bankmanagementsystem.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.bms.bankmanagementsystem.model.entity.Customer;
import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.repository.CustomerRepository;
import com.bms.bankmanagementsystem.service.JwtService;
import com.bms.bankmanagementsystem.utility.helper.ResponseStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${JWT.secretToken}")
    private String secretToken;

    @Value("${JWT.expiration}")
    private int expiration;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseModel generateJwt(JwtModel jwtModel) {
        log.info("JWT SERVICE IMPL - GENERATE JWT");
        ResponseModel responseModel = new ResponseModel();

        System.out.print("JWT MODEL : "+ jwtModel);

        try {

            if( jwtModel != null && !jwtModel.getUser().isBlank() && !jwtModel.getPassword().isBlank() ){

                //Email ID
                if(jwtModel.getUser().contains("@")){
                    Customer customer =  customerRepository.findByEmail(jwtModel.getUser());

                    if( customer != null && !customer.toString().isBlank()){

                        //Check password matches
                        if(Objects.equals(customer.getPassword(), jwtModel.getPassword())){
                            long currentMilliSecond = System.currentTimeMillis();
                            long expirationMilliSecond = System.currentTimeMillis() + expiration * 1000L;
                            String jwtToken = Jwts.builder().setSubject(jwtModel.toString())
                                    .setIssuedAt(new Date(currentMilliSecond))
                                    .setExpiration(new Date(expirationMilliSecond))
                                    .signWith(SignatureAlgorithm.HS256, secretToken)
                                    .compact();

                            responseModel.setData(jwtToken);
                            responseModel.setMessage(ResponseStatusEnum.Success);
                            responseModel.setStatusCode(HttpStatus.OK.value());
                        }
                        else{
                            responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                            responseModel.setMessage(ResponseStatusEnum.Failure);
                            responseModel.setData("Invalid Password");
                        }
                    }
                    else{
                        responseModel.setData("User not found for Email Id is Invalid");
                        responseModel.setMessage(ResponseStatusEnum.Failure);
                        responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    }
                }
                //Phone Number
                else{
                    Customer customer =  customerRepository.findByPhoneNumber(jwtModel.getUser());

                    if( customer != null && !customer.toString().isBlank()){

                        //Check password matches
                        if(Objects.equals(customer.getPassword(), jwtModel.getPassword())){
                            long currentMilliSecond = System.currentTimeMillis();
                            long expirationMilliSecond = System.currentTimeMillis() + expiration * 1000L;
                            String jwtToken = Jwts.builder().setSubject(jwtModel.toString())
                                    .setIssuedAt(new Date(currentMilliSecond))
                                    .setExpiration(new Date(expirationMilliSecond))
                                    .signWith(SignatureAlgorithm.HS256, secretToken)
                                    .compact();

                            responseModel.setData(jwtToken);
                            responseModel.setMessage(ResponseStatusEnum.Success);
                            responseModel.setStatusCode(HttpStatus.OK.value());
                        }
                        else{
                            responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                            responseModel.setMessage(ResponseStatusEnum.Failure);
                            responseModel.setData("Invalid Password");
                        }
                    }
                    else{
                        responseModel.setData("User not found for Phone Number is Invalid");
                        responseModel.setMessage(ResponseStatusEnum.Failure);
                        responseModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    }
                }

            }
            else{
                ArrayList<String> failureData = new ArrayList<>();

                if(jwtModel == null || jwtModel.getUser().isBlank()) {
                    failureData.add("PhoneNumber/Email ID is Empty");
                }
                else if(jwtModel.getPassword().isBlank()){
                    failureData.add("Password is Empty");
                }
                else{
                    failureData.add("Contact Tech Team");
                }

                responseModel.setData(failureData.toString());
                responseModel.setMessage(ResponseStatusEnum.Failure);
                responseModel.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        }catch (Exception exception){
            log.error("EXCEPTION OCCURRED IN GENERATE JWT - "+ exception);
        }
        return responseModel;
    }

    @Override
    public ResponseModel verifyJwt(String jwtToken) {
        log.info("JWT SERVICE - VERIFY JWT");

        ResponseModel responseModel = new ResponseModel<>();

        try {
            Claims  claims = Jwts.parserBuilder().setSigningKey(secretToken).build().parseClaimsJws(jwtToken).getBody();

            responseModel.setData("VERIFIED");
            responseModel.setMessage(ResponseStatusEnum.Success);
            responseModel.setStatusCode(HttpStatus.OK.value());

        }catch(ExpiredJwtException jwtExpiration){
            responseModel.setData("JWT Expired");
            responseModel.setMessage(ResponseStatusEnum.Failure);
            responseModel.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        catch (Exception e){
            log.error("ERROR OCCURRED - JWT SERVICE - VERIFY JWT : ", e);

            responseModel.setData("Contact tech support");
            responseModel.setMessage(ResponseStatusEnum.Failure);
            responseModel.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return responseModel;
    }


}
