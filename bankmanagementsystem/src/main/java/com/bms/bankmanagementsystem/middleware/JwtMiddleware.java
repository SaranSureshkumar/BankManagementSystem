package com.bms.bankmanagementsystem.middleware;

import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import com.bms.bankmanagementsystem.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtMiddleware {

    @Autowired
    private JwtService jwtService;
    public ResponseModel generateJwt(JwtModel jwtModel){
        return jwtService.generateJwt(jwtModel);
    }

    public ResponseModel verifyJwt(String jwtToken) {
        return jwtService.verifyJwt(jwtToken);
    }
}
