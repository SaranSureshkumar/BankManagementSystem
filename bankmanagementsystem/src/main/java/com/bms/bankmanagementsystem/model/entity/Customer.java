package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Customer")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    int customerId;

    @Column(name = "FirstName")
    String firstName;

    @Column(name = "LastName")
    String lastName;

    @Column(name = "Email")
    String email;

    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Column(name = "Password")
    String password;

    @Column(name = "Address")
    String address;

    @Column(name = "City")
    String city;

    @Column(name = "State")
    String state;

    @Column(name = "PostalCode")
    String postalCode;

    @Column(name = "Country")
    String country;

    @Column(name = "CreateAt")
    String createdAt;

    @Column(name = "UpdatedAt")
    String updatedAt;
}
