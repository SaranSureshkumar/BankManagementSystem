package com.bms.bankmanagementsystem.service;

import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public interface JwtService {
    public ResponseModel generateJwt(JwtModel jwtModel);
}
