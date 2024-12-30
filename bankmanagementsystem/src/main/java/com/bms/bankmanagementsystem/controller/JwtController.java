package com.bms.bankmanagementsystem.controller;

import com.bms.bankmanagementsystem.middleware.JwtMiddleWare;
import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JwtController {

    @Autowired
    private JwtMiddleWare jwtMiddleWare;

    @PostMapping(value = "/generateJwt", name = "Generate JWT")
    public ResponseModel generateJwt(@RequestBody JwtModel jwtModel){
        log.info("JWT CONTROLLER - GENERATE JWT");
        return jwtMiddleWare.generateJwt(jwtModel);
    }
}
