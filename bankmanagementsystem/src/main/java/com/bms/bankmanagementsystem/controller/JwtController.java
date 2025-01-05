package com.bms.bankmanagementsystem.controller;

import com.bms.bankmanagementsystem.middleware.JwtMiddleware;
import com.bms.bankmanagementsystem.model.request.JwtModel;
import com.bms.bankmanagementsystem.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jwt", name = "JWT Controller")
@Slf4j
public class JwtController {

    @Autowired
    private JwtMiddleware jwtMiddleWare;

    @PostMapping(value = "/generateJwt", name = "Generate JWT")
    public ResponseModel generateJwt(@RequestBody JwtModel jwtModel){
        log.info("JWT CONTROLLER - GENERATE JWT");
        return jwtMiddleWare.generateJwt(jwtModel);
    }

    @PostMapping(value = "/verifyJwt", name = "Verify JWT")
    public ResponseModel verifyJwt(@RequestHeader("Authorization") String jwtToken){
        log.info("JWT CONTROLLER - VERIFY JWT");
        return jwtMiddleWare.verifyJwt(jwtToken);
    }
}
