package com.bms.bankmanagementsystem.model.response;


import com.bms.bankmanagementsystem.utility.helper.ResponseStatusEnum;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseModel<T> {

    private ResponseStatusEnum message;

    private T data;

    private int statusCode;
}
