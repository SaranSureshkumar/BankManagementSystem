package com.bms.bankmanagementsystem.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateCustomer {

    private Integer customerId;

    private String customerKey;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private LocalDateTime updatedAt;
}
