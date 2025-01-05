package com.bms.bankmanagementsystem.service;

import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public interface JwtService {
    ResponseModel generateJwt(JwtModel jwtModel);

    ResponseModel verifyJwt(String jwtToken);
}
