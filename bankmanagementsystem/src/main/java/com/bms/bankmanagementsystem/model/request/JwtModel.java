package com.bms.bankmanagementsystem.model.request;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtModel {

    String user;

    String password;
}
